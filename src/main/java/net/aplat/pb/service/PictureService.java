package net.aplat.pb.service;

import net.aplat.pb.bo.PictureIndexBO;
import net.aplat.pb.common.PictureBrowserConf;
import net.aplat.pb.dao.PictureSetDao;
import net.aplat.pb.entity.Picture;
import net.aplat.pb.entity.PictureSet;
import net.aplat.pb.exception.IllegalFileAccessException;
import net.aplat.pb.exception.IllegalGroupException;
import net.aplat.pb.util.AlphanumComparator;
import net.aplat.pb.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PictureService {

    private final Logger logger = LoggerFactory.getLogger(PictureService.class);
    private final PictureBrowserConf pictureBrowserConf;
    private final PictureSetDao pictureSetDao;

    public PictureService(PictureBrowserConf pictureBrowserConf, PictureSetDao pictureSetDao) {
        this.pictureBrowserConf = pictureBrowserConf;
        this.pictureSetDao = pictureSetDao;
    }

    public List<PictureIndexBO> getIndex(String group, String path) throws IllegalGroupException {
        try {
            File dir = FileUtils.getFile(pictureBrowserConf.getGroup(group), path);
            return getSortedFiles(dir)
                    .stream().map(this::toPictureIndexBO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IllegalFileAccessException e) {
            logger.info("IllegalFileAccessError: {}", e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public List<String> getPictureList(final String group, final String path) throws IllegalGroupException {
        try {
            return getSortedFiles(FileUtils.getFile(pictureBrowserConf.getGroup(group), path)).stream()
                    .map(file -> path + File.separator + file.getName())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalFileAccessException e) {
            logger.info("IllegalFileAccessError: {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    public Optional<File> getPicture(String group, String path) throws IllegalGroupException {
        try {
            return Optional.of(FileUtils.getFile(pictureBrowserConf.getGroup(group), path));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalFileAccessException e) {
            logger.info("IllegalFileAccessError: {}", e.getMessage());
        }
        return Optional.empty();
    }

    public void scanPictureSets(String group, boolean force) throws IllegalGroupException {
        File root = new File(pictureBrowserConf.getGroup(group));
        List<File> sets = getSortedFiles(root);
        logger.info("Scanning root '{}', total sets: {}", root.getAbsolutePath(), sets.size());
        for (File set : sets) {
            String title = set.getName();
            logger.info("Scanning '{}'", title);
            PictureSet pictureSet = pictureSetDao.findPictureSetByTitleAndGroupName(title, group).orElse(new PictureSet(title, group));
            if (pictureSet.getId() != null && !force) {
                logger.info("Skipped '{}'", title);
                continue;
            }
            pictureSet.getPictures().clear();
            List<File> pictures = getSortedFiles(set);
            for (int i = 0; i < pictures.size(); i++) {
                File pic = pictures.get(i);
                Picture picture = new Picture();
                picture.setName(pic.getName());
                picture.setOrderNum(i);
                picture.setPath(title);
                picture.setPictureSet(pictureSet);
                pictureSet.getPictures().add(picture);
            }
            pictureSetDao.save(pictureSet);
        }
    }


    private PictureIndexBO toPictureIndexBO(File dir) {
        List<File> files = getSortedFiles(dir).stream().filter(f -> f.getName().endsWith(".jpg")
                || f.getName().endsWith(".png")
                || f.getName().endsWith(".gif")
                || f.getName().endsWith(".jpeg")).collect(Collectors.toList());
        if (files.size() == 0) {
            return null;
        }
        return new PictureIndexBO(dir.getName(), dir.getName() + File.separator + files.get(0).getName());
    }

    private List<File> getSortedFiles(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                List<File> list = Arrays.asList(files);
                list.sort((a, b) -> new AlphanumComparator().compare(a.getName(), b.getName()));
                return list;
            }
        }
        return Collections.emptyList();
    }

}

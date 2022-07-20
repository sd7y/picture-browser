package net.aplat.pb.service;

import net.aplat.pb.bo.PictureIndexBO;
import net.aplat.pb.common.PBConfiguration;
import net.aplat.pb.exception.IllegalFileAccessError;
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
    private final PBConfiguration pbConfiguration;

    public PictureService(PBConfiguration pbConfiguration) {
        this.pbConfiguration = pbConfiguration;
    }

    public List<PictureIndexBO> getIndex(String path) {
        try {
            File dir = FileUtils.getFile(pbConfiguration.getRootPath(), path);
            return getSortedFiles(dir)
                    .stream().map(this::toPictureIndexBO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IllegalFileAccessError e) {
            logger.info("IllegalFileAccessError: {}", e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public List<String> getPictureList(final String path) {
        try {
            return getSortedFiles(FileUtils.getFile(pbConfiguration.getRootPath(), path)).stream()
                    .map(file -> path + File.separator + file.getName())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalFileAccessError e) {
            logger.info("IllegalFileAccessError: {}", e.getMessage());
        }
        return Collections.emptyList();
    }
    public Optional<File> getPicture(String path) {
        try {
            return Optional.of(FileUtils.getFile(pbConfiguration.getRootPath(), path));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalFileAccessError e) {
            logger.info("IllegalFileAccessError: {}", e.getMessage());
        }
        return Optional.empty();
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
//                list.sort((a, b) -> {
//                    try {
//                        Integer ia = Integer.parseInt(a.getName().substring(0, a.getName().length() - 4));
//                        Integer ib = Integer.parseInt(b.getName().substring(0, b.getName().length() - 4));
//                        return ia - ib;
//                    } catch (Exception e) {
//                        return 0;
//                    }
//                });
                list.sort((a, b) -> new AlphanumComparator().compare(a.getName(), b.getName()));
                return list;
            }
        }
        return Collections.emptyList();
    }

}

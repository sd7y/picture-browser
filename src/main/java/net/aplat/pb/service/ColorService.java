package net.aplat.pb.service;

import net.aplat.pb.common.PictureBrowserConf;
import net.aplat.pb.dao.LabelDao;
import net.aplat.pb.dao.PictureDao;
import net.aplat.pb.dao.PictureSetDao;
import net.aplat.pb.entity.Label;
import net.aplat.pb.entity.Picture;
import net.aplat.pb.entity.PictureSet;
import net.aplat.pb.exception.IllegalGroupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ColorService {

    private static final String COLOR = "全彩";
    private static final String GRAYSCALE = "黑白";
    private final Logger logger = LoggerFactory.getLogger(ColorService.class);

    private final PictureBrowserConf pictureBrowserConf;
    private final PictureSetDao pictureSetDao;
    private final PictureDao pictureDao;
    private final LabelDao labelDao;

    public ColorService(PictureBrowserConf pictureBrowserConf, PictureSetDao pictureSetDao, PictureDao pictureDao, LabelDao labelDao) {
        this.pictureBrowserConf = pictureBrowserConf;
        this.pictureSetDao = pictureSetDao;
        this.pictureDao = pictureDao;
        this.labelDao = labelDao;
    }

    public void scan(String group, boolean force) throws IllegalGroupException {
        List<PictureSet> pictureSetList = pictureSetDao.findPictureSetsByGroupName(group);
        for (PictureSet pictureSet : pictureSetList) {
            if (!pictureSet.anyLabelMatch(COLOR, GRAYSCALE) || force) {
                scan(pictureSet);
            }
        }
    }

    private void scan(PictureSet pictureSet) throws IllegalGroupException {
        Label colorLabel = labelDao.getLabelByName(COLOR).orElseGet(() -> labelDao.save(new Label(COLOR)));
        Label grayscaleLabel = labelDao.getLabelByName(GRAYSCALE).orElseGet(() -> labelDao.save(new Label(GRAYSCALE)));
        String root = pictureBrowserConf.getGroup(pictureSet.getGroupName());
        int grayscaleCount = 0;
        for (Picture picture : pictureSet.getPictures()) {
            File image = new File(root + File.separator + picture.getPath() + File.separator + picture.getName());
            try {
                picture.removeLabel(grayscaleLabel, colorLabel);
                if (!isColor(image)) {
                    picture.getLabels().add(grayscaleLabel);
                    grayscaleCount++;
                } else {
                    picture.getLabels().add(colorLabel);
                }
                pictureDao.save(picture);
            } catch (IOException e) {
                logger.error("An error has occurred when check the image '{}' color.", image.getAbsolutePath(), e);
            }
        }
        pictureSet.removeLabel(colorLabel, grayscaleLabel);
        // 黑白的图像少于 1/5, 则认为改图集是彩色的
        pictureSet.getLabels().add(grayscaleCount * 5 < pictureSet.getPictures().size() ? colorLabel : grayscaleLabel);
        pictureSetDao.save(pictureSet);
    }


    private boolean isColor(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;
//        int step = hasAlphaChannel ? 4 : 3;
        int step = 120;
        int total = pixels.length / step;
        int color = 0;
        for (int i = hasAlphaChannel ? 1 : 0; i < pixels.length - 4; i += step) {
            int b = pixels[i] & 0xff;
            int g = pixels[i + 1] & 0xff;
            int r = pixels[i + 2] & 0xff;
            if (255 * 3 - (r + g + b) < 50) {
                // 白色不做统计
                total--;
                continue;
            }
//            float[] hsb = rgb2hsb(r,g,b);
//            if (!(hsb[1] == 0 || hsb[2] == 0 || (hsb[1] < 0.15 && hsb[2] < 0.15))) {
//                color++;
//            }
            if (Math.max(Math.max(Math.abs(r - g), Math.abs(g - b)), Math.abs(r - b)) > 50) {
                color++;
            }
        }
//        System.out.println(color + ", " + total);
        // 彩色数量超过 1/3 才算是彩色, 黑白的图片有一定概率包含若干个彩色像素
        return color * 3 > total;

    }
    public static float[] rgb2hsb(int rgbR, int rgbG, int rgbB) {
        assert 0 <= rgbR && rgbR <= 255;
        assert 0 <= rgbG && rgbG <= 255;
        assert 0 <= rgbB && rgbB <= 255;
        int[] rgb = new int[] { rgbR, rgbG, rgbB };
        float[] hsb = new float[5];
        Arrays.sort(rgb);
        int max = rgb[2];
        int min = rgb[0];

        float hsbB = max / 255.0f;
        float hsbS = max == 0 ? 0 : (max - min) / (float) max;

        float hsbH = 0;
        if (max == rgbR) {
            float v = (rgbG - rgbB) * 60f / (max - min);
            if (rgbG >= rgbB) {
                hsbH = v + 0;
            } else {
                hsbH = v + 360;
            }
        } else if (max == rgbG) {
            hsbH = (rgbB - rgbR) * 60f / (max - min) + 120;
        } else if (max == rgbB) {
            hsbH = (rgbR - rgbG) * 60f / (max - min) + 240;
        }
        hsb[0] = hsbH;
        hsb[1] = hsbS;
        hsb[2] = hsbB;
        return hsb;
    }

}

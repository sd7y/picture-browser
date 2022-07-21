package net.aplat.pb.controller;

import net.aplat.pb.exception.IllegalGroupException;
import net.aplat.pb.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

@Controller
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final PictureService pictureService;

    public FileController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping(value = "/{group}/picture", params = {"path"})
    public void getPicture(final HttpServletResponse response, @PathVariable String group, @RequestParam("path") String path) throws IllegalGroupException {
        Optional<File> file = pictureService.getPicture(group, path);
        if (file.isPresent()) {
            File image = file.get();
            MediaTypeFactory.getMediaType(image.getAbsolutePath()).ifPresent(m -> response.setContentType(m.toString()));
            try (FileInputStream fis = new FileInputStream(image); OutputStream os = response.getOutputStream()) {
                byte[] bytes = new byte[1024];
                while (fis.read(bytes) != -1) {
                    os.write(bytes);
                }
                os.flush();
            } catch (FileNotFoundException e) {
                logger.info("File {} not found!", path);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                logger.info("Read file {} error!", path, e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            logger.info("File {} not found!", path);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}




package net.aplat.pb.controller;

import net.aplat.pb.exception.IllegalGroupException;
import net.aplat.pb.service.ColorService;
import net.aplat.pb.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PictureService pictureService;
    private final ColorService colorService;

    public AdminController(PictureService pictureService, ColorService colorService) {
        this.pictureService = pictureService;
        this.colorService = colorService;
    }

    @RequestMapping("/{group}/scan")
    public ModelAndView scanPictureSet(@PathVariable String group, @RequestParam(value = "force", required = false) Boolean force) throws IllegalGroupException {
        force = force != null;
        pictureService.scanPictureSets(group, force);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("message");
        modelAndView.addObject("message", "Success");
        return modelAndView;
    }
    @RequestMapping("/{group}/scan/color")
    public ModelAndView scanColorForPictureSet(@PathVariable String group, @RequestParam(value = "force", required = false) Boolean force) throws IllegalGroupException {
        force = force != null;
        colorService.scan(group, force);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("message");
        modelAndView.addObject("message", "Success");
        return modelAndView;
    }
}

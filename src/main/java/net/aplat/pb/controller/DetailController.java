package net.aplat.pb.controller;

import net.aplat.pb.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DetailController {

    private final PictureService pictureService;

    public DetailController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping(value = "/detail")
    public ModelAndView detail(@RequestParam("title") String title) {
        List<String> pictureList = pictureService.getPictureList(title);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("detail");
        modelAndView.addObject("list", pictureList);
        modelAndView.addObject("title", title);
        return modelAndView;
    }
}

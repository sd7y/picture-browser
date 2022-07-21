package net.aplat.pb.controller;

import net.aplat.pb.exception.IllegalGroupException;
import net.aplat.pb.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/{group}/detail")
    public ModelAndView detail(@PathVariable("group") String group,
            @RequestParam("title") String title) throws IllegalGroupException {
        List<String> pictureList = pictureService.getPictureList(group, title);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("detail");
        modelAndView.addObject("list", pictureList);
        modelAndView.addObject("group", group);
        modelAndView.addObject("title", title);
        return modelAndView;
    }
}

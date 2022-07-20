package net.aplat.pb.controller;

import net.aplat.pb.bo.PictureIndexBO;
import net.aplat.pb.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DetailController extends PageController{

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    private final PictureService pictureService;

    public DetailController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping(value = "/detail")
    public ModelAndView detail(@RequestParam("pageNum") Integer pageNum,
                               @RequestParam("title") String title,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        List<String> all = pictureService.getPictureList(title);
        int total = all.size();
        List<String> onePage = all.subList((pageNum - 1) * pageSize, Math.min(pageNum * pageSize, total));

        ModelAndView modelAndView = page("detail", onePage, pageNum, pageSize, total);
        modelAndView.addObject("title", title);
        return modelAndView;
    }
}

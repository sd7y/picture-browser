package net.aplat.pb.controller;

import net.aplat.pb.bo.PictureIndexBO;
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

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    private final PictureService pictureService;

    public DetailController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping(value = "/{group}/detail")
    public ModelAndView detail(@PathVariable("group") String group,
                               @RequestParam("title") String title,
                               @RequestParam(value = "pageNum", required = false) Integer pageNum,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize) throws IllegalGroupException {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        List<String> pictureList = pictureService.getPictureList(group, title);

        int total = pictureList.size();
        int start = (pageNum - 1) * pageSize;
        if (start >= total) {
            start = 0;
        }
        List<String> onePage = pictureList.subList(start, Math.min(pageNum * pageSize, total));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("detail");
        modelAndView.addObject("list", onePage);
        modelAndView.addObject("group", group);
        modelAndView.addObject("title", title);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("totalCount", total);
        modelAndView.addObject("pageCount", total % pageSize == 0 ? total / pageSize : total / pageSize + 1);
        return modelAndView;
    }
}

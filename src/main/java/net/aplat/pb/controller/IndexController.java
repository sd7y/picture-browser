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
@RequestMapping
public class IndexController {

    private static final Integer DEFAULT_PAGE_SIZE = 9;

    private final PictureService pictureService;

    public IndexController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping("/")
    public ModelAndView index() throws IllegalGroupException {
        return index("default");
    }

    @RequestMapping("/{group}")
    public ModelAndView index(@PathVariable("group") String group) throws IllegalGroupException {
        return page(group,1, DEFAULT_PAGE_SIZE);
    }

    @RequestMapping(value = "/{group}/page/{pageNum}")
    public ModelAndView page(@PathVariable("group") String group,
                             @PathVariable("pageNum") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize) throws IllegalGroupException {
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        List<PictureIndexBO> all = pictureService.getIndex(group, "/");
        int total = all.size();
        List<PictureIndexBO> onePage = all.subList((pageNum - 1) * pageSize, Math.min(pageNum * pageSize, total));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page");
        modelAndView.addObject("list", onePage);
        modelAndView.addObject("group", group);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("totalCount", total);
        modelAndView.addObject("pageCount", total % pageSize == 0 ? total / pageSize : total / pageSize + 1);
        return modelAndView;
    }
}

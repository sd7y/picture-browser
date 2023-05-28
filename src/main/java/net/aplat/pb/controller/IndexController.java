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
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class IndexController {

    private static final Integer DEFAULT_PAGE_SIZE = 9;

    private final PictureService pictureService;

    public IndexController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping("/")
    public ModelAndView index(@RequestParam(value = "s", required = false) String search) throws IllegalGroupException {
        return index("default", search);
    }

    @RequestMapping("/{group}")
    public ModelAndView index(@PathVariable("group") String group,
                              @RequestParam(value = "s", required = false) String search) throws IllegalGroupException {
        return page(group,1, DEFAULT_PAGE_SIZE, search);
    }

    @RequestMapping(value = "/{group}/page/{pageNum}")
    public ModelAndView page(@PathVariable("group") String group,
                             @PathVariable("pageNum") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "s", required = false) String search) throws IllegalGroupException {
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        List<PictureIndexBO> all = pictureService.getIndex(group, "/");
        if (search != null && search.trim().length() > 0) {
            System.out.println("Search word is: " + search);
            all = all.stream().filter(pi -> pi.getTitle().contains(search.trim())).collect(Collectors.toList());
        }
        int total = all.size();
        int start = (pageNum - 1) * pageSize;
        if (start >= total) {
            start = 0;
        }
        List<PictureIndexBO> onePage = all.subList(start, Math.min(pageNum * pageSize, total));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page");
        modelAndView.addObject("list", onePage);
        modelAndView.addObject("group", group);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("totalCount", total);
        modelAndView.addObject("pageCount", total % pageSize == 0 ? total / pageSize : total / pageSize + 1);
        modelAndView.addObject("search", search != null && search.trim().length() > 0 ? search : "");
        return modelAndView;
    }
}

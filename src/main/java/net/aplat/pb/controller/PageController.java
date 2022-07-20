package net.aplat.pb.controller;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public abstract class PageController {

    protected ModelAndView page(String viewName, List list, int pageNum, int pageSize, int total) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        modelAndView.addObject("list", list);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("totalCount", total);
        modelAndView.addObject("pageCount", total % pageSize == 0 ? total / pageSize : total / pageSize + 1);
        return modelAndView;
    }
}

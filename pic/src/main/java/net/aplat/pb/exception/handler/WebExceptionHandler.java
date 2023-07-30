package net.aplat.pb.exception.handler;

import net.aplat.pb.exception.IllegalGroupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class WebExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(value = IllegalGroupException.class)
    ModelAndView handle(HttpServletRequest request, IllegalGroupException e) {
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(value = Exception.class)
    ModelAndView handle(HttpServletRequest request, Exception e) {
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("500");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }
}

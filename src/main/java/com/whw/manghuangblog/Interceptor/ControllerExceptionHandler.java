package com.whw.manghuangblog.Interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception ex) throws Exception {
        logger.error("Request URL : {}, Exception ：{}", request.getRequestURI(), ex);

        if(AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class)  != null){
            throw ex;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURI());
        mv.addObject("exception", ex);
        mv.setViewName("error/error");
        return mv;
    }
}

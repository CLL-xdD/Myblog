package com.wmz.blog.comment.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义异常处理器
 */
@ControllerAdvice
public class BlogExceptionHandler {

    private final Logger logger =  LoggerFactory.getLogger(BlogExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request,Exception e){
        //打印日志信息
        logger.error("Request URL : {},Exception : ",request.getRequestURI(),e);

        //保存信息
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url",request.getRequestURI());
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }


}

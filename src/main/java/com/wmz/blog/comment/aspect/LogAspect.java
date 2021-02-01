package com.wmz.blog.comment.aspect;

import com.alibaba.fastjson.JSON;
import com.wmz.blog.system.model.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * 操作日志信息
 */

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 监听blog包下的所有日志信息
     */
    @Pointcut("execution(* com.wmz.blog.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void beforeLog(JoinPoint joinPoint){
        //获取用户访问信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取uri
        String uri = request.getRequestURI();
        //获取类名
        String className = joinPoint.getSignature().getDeclaringTypeName();
        //获取方法名
        String name = className+""+joinPoint.getSignature().getName();
        //获取IP地址
        String ip = request.getRemoteAddr();
        SysLog sysLog = new SysLog();
        sysLog.setClassName(className);
        sysLog.setIp(ip);
        sysLog.setMethod(name);
        sysLog.setOrgs(JSON.toJSONString(joinPoint.getArgs()));
        sysLog.setUrl(uri);
        sysLog.setCreateTime(LocalDateTime.now());
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null){
            sysLog.setCreateUser((String) session.getAttribute("user"));
        }

    }

    @After("log()")
    public void afterLog(){

    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void returnLog(Object result){
        logger.info("Return ------ {}",result );
    }
}

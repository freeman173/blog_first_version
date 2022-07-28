package com.example.blogapi_springboot.common.aop;


import com.alibaba.fastjson.JSON;
import com.example.blogapi_springboot.utils.HttpContextUtils;
import com.example.blogapi_springboot.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.spi.http.HttpContext;
import java.lang.reflect.Method;

@Component
@Aspect//面向切面注解：定义了通知与切点之间的关系
/*
记录日志:@Slf4j是用作日志输出的，一般会在项目每个类的开头加入该注解，如果不写下面这段代码，并且想用log
private final Logger logger = LoggerFactory.getLogger(当前类名.class);
就可以用@Slf4来代替;这样就省去这段很长的代码。
      */

@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.example.blogapi_springboot.common.aop.LogAnnotation)")
    public void pt(){}

//    环绕通知：在“pt()”方法前后环绕通知，起一个增强效果
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Long begintime=System.currentTimeMillis();

//        执行方法
        Object result=joinPoint.proceed();
//        执行时长
        Long time=System.currentTimeMillis()-begintime;
//        保存日志
        recordlog(joinPoint,time);

        return result;
    }

    private void recordlog(ProceedingJoinPoint joinPoint,Long time){
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        Method method=signature.getMethod();
        LogAnnotation logAnnotation=method.getAnnotation(LogAnnotation.class);
        log.info("======================log start =============================");
        log.info("module:{}",logAnnotation.module());
        log.info("operation:{}",logAnnotation.operator());

//        请求的方法名
        String className=joinPoint.getTarget().getClass().getName();
        String methodName=signature.getName();
        log.info("request_method:{}",className+"."+methodName+"()");

//        请求的参数
        Object[] args=joinPoint.getArgs();
//        博主之类传入了args[0]
        String params= JSON.toJSONString(args);
        log.info("params:{}",params);

//        获取request，设置IP地址
        HttpServletRequest request= HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddress(request));

        log.info("执行时间:{} ms",time);
        log.info("======================log end =============================");

    }




}

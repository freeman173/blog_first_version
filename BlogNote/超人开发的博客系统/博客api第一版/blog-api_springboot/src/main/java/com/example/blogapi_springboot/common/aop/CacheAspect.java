package com.example.blogapi_springboot.common.aop;


import com.alibaba.fastjson.JSON;
import com.example.blogapi_springboot.utils.HttpContextUtils;
import com.example.blogapi_springboot.utils.IpUtils;
import com.example.blogapi_springboot.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Duration;


@Component
@Aspect//面向切面注解：定义了通知与切点之间的关系
/*
记录日志:@Slf4j是用作日志输出的，一般会在项目每个类的开头加入该注解，如果不写下面这段代码，并且想用log
private final Logger logger = LoggerFactory.getLogger(当前类名.class);
就可以用@Slf4来代替;这样就省去这段很长的代码。
      */

@Slf4j
public class CacheAspect {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Pointcut("@annotation(com.example.blogapi_springboot.common.aop.Cache)")
    public void pt(){}

    //    环绕通知：在“pt()”方法前后环绕通知，起一个增强效果
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp)  {
        Signature signature= pjp.getSignature();
//        类名
        String className=pjp.getClass().getSimpleName();
//        调用的方法名
        String methodName=signature.getName();

        Class[] parameterTypes=new Class[pjp.getArgs().length];
        Object[] args=pjp.getArgs();

//        参数
        String params="";
        for(int i=0;i<args.length;i++){
            if(args[i]!=null){
                params+=JSON.toJSONString(args[i]);
                parameterTypes[i]=args[i].getClass();
            }else {
                parameterTypes[i]=null;
            }
        }
        if(StringUtils.isNotEmpty(params)){
//            加个密
            params= DigestUtils.md5Hex(params);
        }
        Method method= null;
        try {
            method = pjp.getSignature().getDeclaringType().getMethod(methodName,parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
//        获取cache注解
        Cache annotation=method.getAnnotation(Cache.class);
//        缓存过期时间
        long expire=annotation.expire();
//        缓存名
        String name=annotation.name();
//        从redis取值
        String redisKey=name+"::"+className+"::"+methodName+"::"+params;
        String redisvalue=redisTemplate.opsForValue().get(redisKey);
        if(StringUtils.isNotEmpty(redisvalue)){

            log.info("走缓存：{},{}",className,methodName);
            return JSON.parseObject(redisvalue, Result.class);

        }
        Object proceed= null;
        try {
            proceed = pjp.proceed();
            redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存:{},{}",className,methodName);
            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return Result.failure(-999,"系统错误");


    }













}


package com.example.blogapi_springboot.common.aop;


import java.lang.annotation.*;

@Target({ ElementType.METHOD})//代表该注解可以放在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1*60*1000;
//    缓存标识
    String name() default "";

}

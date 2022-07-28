package com.example.blogapi_springboot.common.aop;


import java.lang.annotation.*;


@Target({ ElementType.METHOD})//代表该注解可以放在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface LogAnnotation {

    String module() default "";
    String operator() default "";

}

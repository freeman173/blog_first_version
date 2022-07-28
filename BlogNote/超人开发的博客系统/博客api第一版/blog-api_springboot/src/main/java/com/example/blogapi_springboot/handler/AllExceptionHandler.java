package com.example.blogapi_springboot.handler;


import com.example.blogapi_springboot.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice//对加了@controller的注解的方法做拦截处理（AOP（面向切面编程）的实现）
public class AllExceptionHandler {


    @ExceptionHandler(Exception.class)//处理Exception.class异常！！
    @ResponseBody//返回给前端json数据！！
    public Result doException(Exception exception){
        exception.printStackTrace();
        return Result.failure(-999,"系统异常！！");
    }
}

package com.example.blogapi_springboot.controller;
import com.example.blogapi_springboot.dao.pojo.SysUser;
import com.example.blogapi_springboot.utils.UserThreadLocal;
import com.example.blogapi_springboot.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestController {


    @GetMapping ("/test")
    public Result test(){

        SysUser sysUser= UserThreadLocal.get();
        return Result.success(sysUser);
    }
}

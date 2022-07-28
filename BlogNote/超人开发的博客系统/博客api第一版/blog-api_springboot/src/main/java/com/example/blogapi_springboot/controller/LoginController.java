package com.example.blogapi_springboot.controller;


import com.example.blogapi_springboot.service.LoginService;
import com.example.blogapi_springboot.service.impl.LoginServiceImpl;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//登录的控制器
public class LoginController {

    @Autowired
    private LoginService loginServiceImpl;


    @PostMapping("/login")
//    如果参数是放在请求体中，依照application/json传入后台的话，那么后台要用@RequestBody才能接收到
//    @RequestBody LoginParam loginParam:将前端的数据接收过来并自动封装为LoginParam 对象
    public Result login(@RequestBody LoginParam loginParam){

        return loginServiceImpl.login(loginParam);
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){

        return loginServiceImpl.logout(token);
    }

}

package com.example.blogapi_springboot.controller;

import com.example.blogapi_springboot.service.SysUserService;
import com.example.blogapi_springboot.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
//，@RestController=@Controller+@ResponseBody
//@ResponseBody 是用来把返回对象自动序列化成HttpResponse的。@ResponseBody告诉控制器返回对象会被自动序列化成JSON，并且传回HttpResponse这个对象。
//@RequestBody把HttpRequest body映射成一个 transfer or domain object（DTO或者DO），把一个入境（inbound）的HttpRequest的body反序列化成一个Java对象。
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/users/currentUser")
    public Result currentuser(@RequestHeader("Authorization") String token){

        return sysUserService.findUserByToken(token);

    }

}

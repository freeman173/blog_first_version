package com.example.blogapi_springboot.controller;


import com.example.blogapi_springboot.service.RegisterService;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {


    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterParam registerParam){

        System.out.println(registerParam);
        return registerService.register(registerParam);
    }



}

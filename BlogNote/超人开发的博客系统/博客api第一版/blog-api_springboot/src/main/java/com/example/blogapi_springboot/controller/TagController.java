package com.example.blogapi_springboot.controller;


import com.example.blogapi_springboot.service.TagSerivce;
import com.example.blogapi_springboot.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    @Autowired
    private TagSerivce tagSerivce;


    @GetMapping("/tags/hot")
    public Result hot(){
//只显示前6条
        Integer limit=4;
//        返回的数据为一个tag对象列表，在网页调试工具中可以查看（其实没必要写！！）
        return tagSerivce.hots(limit);
    }

    @GetMapping("/tags")
    public Result tags(){

        return tagSerivce.findAllTags();
    }

    @GetMapping("/tags/detail")
    public Result tagsDetail(){


        return tagSerivce.findAllTagsDetail();

    }

    @GetMapping("/tags/detail/{id}")
    public Result tagDetailById(@PathVariable("id") Long id){

        return tagSerivce.tagDetailById(id);

    }



}

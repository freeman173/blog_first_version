package com.example.blogapi_springboot.controller;


import com.example.blogapi_springboot.common.aop.LogAnnotation;
import com.example.blogapi_springboot.service.UploadService;
import com.example.blogapi_springboot.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;


    @PostMapping("/upload")
//    @LogAnnotation(module="上传文件",operator="上传图片")
//    spring框架中专门接收文件的数据类型：MultipartFile
    public Result upload(@RequestParam("image")MultipartFile file){


        return uploadService.upload(file);

    }

}

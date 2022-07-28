package com.example.blogapi_springboot.service;

import com.example.blogapi_springboot.vo.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    Result upload(MultipartFile file);
}

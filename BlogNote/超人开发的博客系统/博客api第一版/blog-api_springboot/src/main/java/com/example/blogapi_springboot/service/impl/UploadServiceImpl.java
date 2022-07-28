package com.example.blogapi_springboot.service.impl;

import com.example.blogapi_springboot.service.UploadService;
import com.example.blogapi_springboot.utils.FileUtils;
import com.example.blogapi_springboot.vo.ErrorCode;
import com.example.blogapi_springboot.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@Service
public class UploadServiceImpl implements UploadService {



    @Override
    public Result upload(MultipartFile file) {

        if (Objects.isNull(file) || file.isEmpty()) {
            //判断非空
            return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        try {

            byte[] bytes = file.getBytes();
            //要存入本地的地址放到path里面
            Path path = Paths.get( FileUtils.UPLOAD_FOLDER + "/");
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(path);
            }
            //           拿到文件的名称
            String fileName=FileUtils.getFileExtension(file);
            FileUtils.getFileByBytes(bytes, FileUtils.UPLOAD_FOLDER, fileName);
//            浏览器端可以直接访问的，但是vue中就不能访问

            return Result.success("http://localhost:8098/Admin/"+fileName);
//            return "http://localhost:8098/Admin/"+fileName;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());


//        return null;
    }
}

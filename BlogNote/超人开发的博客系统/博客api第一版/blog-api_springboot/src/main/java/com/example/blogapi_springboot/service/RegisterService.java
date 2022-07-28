package com.example.blogapi_springboot.service;


import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.RegisterParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional//事务注解：当涉及到数据库操作时，确保操作的一致性
public interface RegisterService {


    Result register(RegisterParam registerParam);
}

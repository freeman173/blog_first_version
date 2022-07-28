package com.example.blogapi_springboot.service;


import com.example.blogapi_springboot.dao.pojo.SysUser;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.LoginParam;

public interface LoginService {


    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    Result logout(String token);
}

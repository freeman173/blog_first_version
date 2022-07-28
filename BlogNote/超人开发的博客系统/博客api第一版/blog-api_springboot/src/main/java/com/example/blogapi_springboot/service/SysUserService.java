package com.example.blogapi_springboot.service;


import com.example.blogapi_springboot.dao.pojo.SysUser;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.UserVo;

public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    void save(SysUser sysUser);

    SysUser findUserByAccount(String account);
}

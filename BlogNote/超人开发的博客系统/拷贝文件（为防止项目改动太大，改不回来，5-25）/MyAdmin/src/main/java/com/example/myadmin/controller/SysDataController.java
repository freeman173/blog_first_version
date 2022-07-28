package com.example.myadmin.controller;


import com.example.myadmin.pojo.SysUser;
import com.example.myadmin.service.UserService;
import com.example.myadmin.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api("Data控制类")//swagger中会生成接口类说明
@RestController
public class SysDataController {

    @Autowired
    private UserService userService;


    //    查询权限表的信息
    @GetMapping(value = "/data/sysUsers")
//    @RequestBody:将前端传过来的参数自动映射为后面的java对象
//    使用map接受参数
    public Result getSysUsers(@RequestParam Map<String,Object> map){


        System.out.println(map);

        return userService.getSysUsers(map);

    }

    @PostMapping("/data/editSysUser")
    public Result editSysUser(@RequestBody SysUser sysUser){

        System.out.println(sysUser);
        return userService.editSysUser(sysUser);
    }

    @GetMapping("/data/deleteSysUser/{id}")

    public Result deleteSysUserById(@PathVariable("id") Long sysUserId){


        return userService.deleteSysUserById(sysUserId);
    }





}

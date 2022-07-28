package com.example.myadmin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.myadmin.mapper.AdminMapper;
import com.example.myadmin.pojo.Admin;
import com.example.myadmin.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {


    @Autowired
    private AdminMapper adminMapper;


    public Admin findAdminByUserName(String userName){
        //生成查询条件
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUserName,userName);
        queryWrapper.last("limit 1");

        Admin admin=(Admin) adminMapper.selectOne(queryWrapper);

        return admin;

    }


    public List<Permission> findPermissionsByAdminId(Long adminId) {

        List<Permission> permissionList= adminMapper.findPermissionsByAdminId(adminId);

        return permissionList;


    }
}

package com.example.myadmin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myadmin.mapper.PermissionMapper;
import com.example.myadmin.model.params.PageParam;
import com.example.myadmin.pojo.Permission;
import com.example.myadmin.service.PermissionService;
import com.example.myadmin.vo.PageResult;
import com.example.myadmin.vo.Result;
import com.example.myadmin.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result listPermission(PageParam pageParam) {
        /*
        1、将permission表中所有数据查询出来
        2、再分个页
         */
//        构建分页器
        Page<Permission> page=new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
//        构建查询条件
        LambdaQueryWrapper<Permission> queryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
//            前端数据的查询条件与权限表中的name对应
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
//        将数据查询出来后再做了一个分页操作
        Page<Permission> permissionPage=permissionMapper.selectPage(page,queryWrapper);
//        将分页数据转化为pageResult对象
        PageResult<Permission> pageResult=new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }


    @Override
    public Result add(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result delete(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.success(null);
    }
}

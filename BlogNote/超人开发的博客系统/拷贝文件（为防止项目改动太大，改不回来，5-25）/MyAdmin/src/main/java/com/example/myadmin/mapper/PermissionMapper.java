package com.example.myadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.myadmin.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}

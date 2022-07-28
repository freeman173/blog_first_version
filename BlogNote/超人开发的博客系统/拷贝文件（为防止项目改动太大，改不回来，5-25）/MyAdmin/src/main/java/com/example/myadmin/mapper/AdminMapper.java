package com.example.myadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myadmin.pojo.Admin;
import com.example.myadmin.pojo.Permission;
import com.example.myadmin.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
//
    @Select("select * from ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})")
    List<Permission> findPermissionsByAdminId(Long adminId);

}

package com.example.myadmin.service;



import com.example.myadmin.pojo.SysUser;
import com.example.myadmin.vo.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public interface UserService {

    Result getSysUsers(Map<String, Object> map);

    Result editSysUser(SysUser sysUser);

    Result deleteSysUserById(Long sysUserId);
}

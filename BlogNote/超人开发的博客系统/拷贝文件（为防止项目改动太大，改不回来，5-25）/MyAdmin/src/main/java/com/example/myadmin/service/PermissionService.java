package com.example.myadmin.service;

import com.example.myadmin.model.params.PageParam;
import com.example.myadmin.pojo.Permission;
import com.example.myadmin.vo.Result;

public interface PermissionService {
    Result listPermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}

package com.example.myadmin.controller;


import com.example.myadmin.model.params.PageParam;
import com.example.myadmin.pojo.Permission;
import com.example.myadmin.service.PermissionService;
import com.example.myadmin.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("Admin控制类")//swagger中会生成接口类说明
@RestController
public class AdminController {

    @Autowired
    private PermissionService permissionService;


//    查询权限表的信息
    @PostMapping("/admin/permission/permissionList")
//    @RequestBody:将前端传过来的参数自动映射为后面的java对象
    public Result listPermission(@RequestBody PageParam pageParam){

        return permissionService.listPermission(pageParam);

    }

    @PostMapping("admin/permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("admin/permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("admin/permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){

        return permissionService.delete(id);
    }






}

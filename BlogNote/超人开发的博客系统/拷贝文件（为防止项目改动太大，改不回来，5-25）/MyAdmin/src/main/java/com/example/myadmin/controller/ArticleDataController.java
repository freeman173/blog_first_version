package com.example.myadmin.controller;


import com.example.myadmin.pojo.SysUser;
import com.example.myadmin.service.ArticleService;
import com.example.myadmin.service.UserService;
import com.example.myadmin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ArticleDataController {
    @Autowired
    private ArticleService articleService;

    //    查询权限表的信息
    @GetMapping(value = "/data/articles")
//    @RequestBody:将前端传过来的参数自动映射为后面的java对象
//    使用map接受参数
    public Result getSysUsers(@RequestParam Map<String,Object> map){

        System.out.println(map);

        return articleService.getArtilces(map);

    }

    /*
        根据文章id删除数据
     */
    @GetMapping("/data/deleteArticle/{id}")

    public Result deleteSysUserById(@PathVariable("id") Long articleId){


        return articleService.deleteArticleById(articleId);
    }

    /*
           返回给前端绘图数据
        */
    @GetMapping("/data/getPieData")
    public Result getPieData(){

        return articleService.getPieData();

    }


    @GetMapping("/data/getLineData")
    public Result getLineData(){

        return articleService.getLineData();

    }


    @GetMapping("/data/getBarData")
    public Result getBarData(){

        return articleService.getBarData();


    }








//    @PostMapping("/data/editSysUser")
//    public Result editSysUser(@RequestBody SysUser sysUser){
//
//        System.out.println(sysUser);
//        return userService.editSysUser(sysUser);
//    }
//
//    @GetMapping("/data/deleteSysUser/{id}")
//
//    public Result deleteSysUserById(@PathVariable("id") Long sysUserId){
//
//
//        return userService.deleteSysUserById(sysUserId);
//    }
//

}

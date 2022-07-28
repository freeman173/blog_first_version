package com.example.blogapi_springboot.controller;


import com.example.blogapi_springboot.common.aop.Cache;
import com.example.blogapi_springboot.common.aop.LogAnnotation;
import com.example.blogapi_springboot.service.impl.ArticleServiceImpl;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.ArticleParam;
import com.example.blogapi_springboot.vo.params.NameParam;
import com.example.blogapi_springboot.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ArticleController {

    @Autowired
    ArticleServiceImpl articleService;

    /*
    首页 文章列表
    @param pageparams
    @return Result

     */


    @PostMapping("/articles")

//    @LogAnnotation(module="文章",operator="获取文章列表")
//    @Cache(expire = 1*10*1000,name = "article")//缓存注解:10s
    public Result listArticles(@RequestBody PageParams pageParams){

//        Integer i=10/0； 测试handler包中的统一异常捕获功能，可在前端页面查看返回到json数据
        return articleService.listArticle(pageParams);


    }

    @PostMapping("/myarticles")
    @LogAnnotation(module="文章",operator="获取文章列表")
//    @Cache(expire = 1*10*1000,name = "article")//缓存注解:10s
    public Result listMyArticles(@RequestBody PageParams pageParams){

//        Integer i=10/0； 测试handler包中的统一异常捕获功能，可在前端页面查看返回到json数据
        return articleService.listMyArticle(pageParams);


    }



//    返回浏览量最多的前几篇文章
    @PostMapping("/articles/hot")
    public Result hotArticle(){

        Integer limit=5;

        return articleService.hotArticle(limit);
    }
    //    返回最新的前几篇文章
    @PostMapping("/articles/new")
    public Result newArticle(){

        Integer limit=5;
        return articleService.newArticle(limit);
    }

//    将文章做一个归档
    @PostMapping("/articles/listArchives")
    public Result listArchives(){

        return articleService.listArchives();
    }



    //    将文章做一个归档
    @PostMapping("/articles/mylistArchives")
    public Result mylistArchives(@RequestBody NameParam nameParam){


        System.out.println(nameParam.getAuthorName());

        return articleService.mylistArchives(nameParam);

    }




//    根据id查找文章
    @PostMapping("/articles/view/{id}")
    public Result findArticleById(@PathVariable("id")Long articleId){

        return articleService.findArticleById(articleId);
    }



    @PostMapping("/articles/publish")
    public Result publish(@RequestBody ArticleParam articleParam){


        return articleService.publish(articleParam);
    }



}

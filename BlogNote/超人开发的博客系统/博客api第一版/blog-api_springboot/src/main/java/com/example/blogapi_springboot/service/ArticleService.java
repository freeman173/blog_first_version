package com.example.blogapi_springboot.service;

import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.ArticleParam;
import com.example.blogapi_springboot.vo.params.NameParam;
import com.example.blogapi_springboot.vo.params.PageParams;
import org.springframework.transaction.annotation.Transactional;



@Transactional
public interface ArticleService {

     /*
    分页查询 文章列表
    @param pageparams
    @return Result


     */

    Result listArticle(PageParams pageParams);

//    查询最热（浏览量最多）文章
    Result hotArticle(Integer limit);

    Result newArticle(Integer limit);

    Result listArchives();

    Result findArticleById(Long articleId);


//    发布文章
    Result publish(ArticleParam articleParam);

    Result listMyArticle(PageParams pageParams);

    Result mylistArchives(NameParam nameParam);
}


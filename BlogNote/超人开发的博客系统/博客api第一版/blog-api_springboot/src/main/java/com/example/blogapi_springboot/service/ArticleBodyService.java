package com.example.blogapi_springboot.service;

import com.example.blogapi_springboot.vo.ArticleBodyVo;

public interface ArticleBodyService {


    ArticleBodyVo findArticleBodyById(Long bodyId);
}

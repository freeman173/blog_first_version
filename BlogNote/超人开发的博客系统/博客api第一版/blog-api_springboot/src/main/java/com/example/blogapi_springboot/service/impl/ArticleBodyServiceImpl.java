package com.example.blogapi_springboot.service.impl;


import com.example.blogapi_springboot.dao.mapper.ArticleBodyMapper;
import com.example.blogapi_springboot.dao.pojo.ArticleBody;
import com.example.blogapi_springboot.service.ArticleBodyService;
import com.example.blogapi_springboot.vo.ArticleBodyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArticleBodyServiceImpl implements ArticleBodyService {

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody=articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo=new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;

    }
}

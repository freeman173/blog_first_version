package com.example.blogapi_springboot.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogapi_springboot.dao.dos.Archives;
import com.example.blogapi_springboot.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
public interface ArticleMapper extends BaseMapper<Article> {


    List<Archives> listArchives();
    IPage<Article> listArticle(Page<Article> page,Long categoryId,Long tagId,String year,String month);

    List<Archives> myListArchives(Long authorId);



}


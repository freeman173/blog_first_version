package com.example.blogapi_springboot.service;

import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.TagVo;

import java.util.List;

public interface TagSerivce {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(Integer limit);

    Result findAllTags();

    Result findAllTagsDetail();

    Result tagDetailById(Long id);
}

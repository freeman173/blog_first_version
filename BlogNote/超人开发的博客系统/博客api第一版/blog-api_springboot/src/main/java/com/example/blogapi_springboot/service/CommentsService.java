package com.example.blogapi_springboot.service;


import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.CommentParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentsService {
/*
   根据文章id查找所有评论
 */
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}

package com.example.blogapi_springboot.controller;


import com.example.blogapi_springboot.service.CommentsService;
import com.example.blogapi_springboot.vo.CommentVo;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    @GetMapping("/comments/article/{id}")
    public Result comments(@PathVariable("id") Long id){

        return commentsService.commentsByArticleId(id);

    }

    @PostMapping("/comments/create/change")
    public Result comment(@RequestBody CommentParam commentParam){

        return commentsService.comment(commentParam);
    }



}

package com.example.blogapi_springboot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blogapi_springboot.dao.mapper.ArticleMapper;
import com.example.blogapi_springboot.dao.mapper.CommentMapper;
import com.example.blogapi_springboot.dao.pojo.Article;
import com.example.blogapi_springboot.dao.pojo.Comment;
import com.example.blogapi_springboot.dao.pojo.SysUser;
import com.example.blogapi_springboot.service.CommentsService;
import com.example.blogapi_springboot.service.SysUserService;
import com.example.blogapi_springboot.utils.UserThreadLocal;
import com.example.blogapi_springboot.vo.CommentVo;
import com.example.blogapi_springboot.vo.ErrorCode;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.UserVo;
import com.example.blogapi_springboot.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserService sysUserService;

/*
1、根据文章id查询评论列表
2、根据作者id查询作者信息
3、如果level=1，去查看它是否有子评论，有的话就提出来（根据parent_id）

 */
    @Override
//    查询父评论
    public Result commentsByArticleId(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
//        level=1：该评论为父评论
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments=commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVos=copylist(comments);

        return Result.success(commentVos);
    }


    @Override
    public Result comment(CommentParam commentParam) {
//获取当前对象
        SysUser sysUser= UserThreadLocal.get();
// 创建评论信息对象
        Comment comment=new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
//        如果是一条子评论，肯定只有一个父评论
        Long parent=commentParam.getParent();
        comment.setParentId(parent==null?0:parent);
        if(parent==null||parent==0){
            comment.setLevel(1);
        }else {
            comment.setLevel(2);
        }
//      为空就填0
        comment.setToUid(comment.getToUid()==null?0: comment.getToUid());
//        插入对象并让对应文章的comments加1
        if(commentMapper.insert(comment)==1){

            Long articleId=commentParam.getArticleId();
            Article article=articleMapper.selectById(articleId);
            article.setCommentCounts(article.getCommentCounts()+1);
            articleMapper.updateById(article);

            return Result.success(comment);
        }
//
        return null;

    }











    private List<CommentVo> copylist(List<Comment> comments) {
        List<CommentVo> commentVos=new ArrayList<>();
        for(Comment comment:comments){

            commentVos.add(copy(comment));
        }
        return commentVos;

    }

    private CommentVo copy(Comment comment){
        CommentVo commentVo=new CommentVo();
//        只能copy一部分信息
        BeanUtils.copyProperties(comment,commentVo);
//        再往里面填充作者信息
        Long authorId=comment.getAuthorId();
        commentVo.setAuthor(sysUserService.findUserVoById(authorId));

//        填充子评论
        Integer level=comment.getLevel();
//        前面查询时已经判断过了，这里是怎么回事呢？
//        回答：后面子评论也需要用到这个函数，这里做一个筛选。
        if(level==1){
            Long id=comment.getId();
//查询子评论
            List<CommentVo> commentVos=findCommentsByParentId(id);
            commentVo.setChildren(commentVos);
        }
//    填充touser（子评论给谁评论的）
        if(level>1){
            Long touid=comment.getToUid();
            UserVo userVo=sysUserService.findUserVoById(touid);
            commentVo.setToUser(userVo);
        }

        return commentVo;
    }

//    查询子评论
    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
//        level=2：该评论为zi评论
        queryWrapper.eq(Comment::getLevel,2);
//        子评论也需要经过copylist！！
        return copylist(commentMapper.selectList(queryWrapper));



    }


}

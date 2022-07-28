package com.example.blogapi_springboot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogapi_springboot.dao.dos.Archives;
import com.example.blogapi_springboot.dao.mapper.ArticleBodyMapper;
import com.example.blogapi_springboot.dao.mapper.ArticleMapper;
import com.example.blogapi_springboot.dao.mapper.ArticleTagMapper;
import com.example.blogapi_springboot.dao.pojo.*;
import com.example.blogapi_springboot.service.ArticleBodyService;
import com.example.blogapi_springboot.service.ArticleService;
import com.example.blogapi_springboot.service.CategoryService;
import com.example.blogapi_springboot.service.ThreadService;
import com.example.blogapi_springboot.utils.TimeUtils;
import com.example.blogapi_springboot.utils.UserThreadLocal;
import com.example.blogapi_springboot.vo.*;
import com.example.blogapi_springboot.vo.params.ArticleParam;
import com.example.blogapi_springboot.vo.params.NameParam;
import com.example.blogapi_springboot.vo.params.PageParams;
import org.apache.ibatis.annotations.Delete;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleBodyService articleBodyService;

    @Autowired
    ThreadService threadService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;


//文章归档:
    @Override
    public Result listArchives() {



        List<Archives> archivesList=articleMapper.listArchives();
        return Result.success(archivesList);
    }



    @Override
    public Result findArticleById(Long articleId) {
        /*
        1、根据id查询文章信息
        2、根据body_id与category_id做关联查询
         */
//        在ArticleVO（将所需要的几张表信息做了组合封装），包含了前端网页的信息！！

//        先查询文章信息Article
        Article article=this.articleMapper.selectById(articleId);
//        再将其转化为ArticleVo
        ArticleVo articleVo=copy(article,true,true,true,true);

//        在查看相应文章后，需要新增阅读数
//        做一个更新操作：如果在主线程里面更新，就会有一个等待时间，这样不太好，需要单独开一个线程池做这个操作！！
        threadService.updateArticleViewCount(articleMapper,article);

        return Result.success(articleVo);


    }

    /*

    1、


     */



    @Override
    public Result publish(ArticleParam articleParam) {
//        获取当前登录用户(登录之后才能发文章)
        SysUser sysUser= UserThreadLocal.get();
/*
1、发布文章，构建Article对象,填充基本信息
 */
        Article article=new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setViewCounts(0);
        Long x=articleParam.getCategory().getId();
        Integer X=new Integer(x.intValue());
        article.setCategoryId(X);
//        先插入article生成articleId，后面需要用到
        this.articleMapper.insert(article);



//将文章标签放入对应表中！！
        List<TagVo> tagVos=articleParam.getTags();
        if(tagVos!=null){
            for(TagVo tagVo:tagVos){
                Long articleId=article.getId();
                ArticleTag articleTag=new ArticleTag();
                articleTag.setTagId(tagVo.getId());
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);

            }
        }
        //将文章body放入对应表中,bodyId会自生成
        ArticleBody articleBody=new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

//        article的bodyId还未设置,现在设置，最后统一插入数据库表
        article.setBodyId(articleBody.getId());
        this.articleMapper.updateById(article);

//       需要返回一个键值对：文章id:str(id值)
        Map<String,String> map=new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }


//    将作者姓名加进去查询
    @Override
    public Result listMyArticle(PageParams pageParams) {

//        Page<Article> page=new Page<>(pageParams.getPage(), pageParams.getPageSize());
//
//        IPage<Article> articleIPage=articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(), pageParams.getMonth());
//        List<Article> records=articleIPage.getRecords();


        String authorname=pageParams.getAuthorName();

        if(authorname.isEmpty()){
            return Result.failure(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        SysUser sysUser=sysUserService.findUserByName(authorname);

        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getAuthorId,sysUser.getId());

        List<Article> articleList=articleMapper.selectList(queryWrapper);


        if(!articleList.isEmpty()) {
            return Result.success(copyList(articleList, true, true, false, false));
        }else {
            return Result.failure(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }


    }



    @Override
//    将作者姓名加进去归档
    public Result mylistArchives(NameParam nameParam) {

        String authorname=nameParam.getAuthorName();

        if(authorname.isEmpty()){
            return Result.failure(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        SysUser sysUser=sysUserService.findUserByName(authorname);

        List<Archives> archivesList=articleMapper.myListArchives(sysUser.getId());



        return Result.success(archivesList);



    }





    //    查询最热文章
    @Override
    public Result hotArticle(Integer limit) {

//        这里直接借助 mybatis-plus内置的查询条件生成对象。
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        下面三句代表的sql语句：select * from article order by viewcounts desc limit 5；
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle,Article::getSummary);
        queryWrapper.last("limit "+limit);

        List<Article> articles=articleMapper.selectList(queryWrapper);

        return Result.success(articles);
    }


//查询最新文章
    @Override
    public Result newArticle(Integer limit) {
//        这里直接借助 mybatis-plus内置的查询条件生成对象。
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        下面三句代表的sql语句：select * from article order by viewcounts desc limit 5；
        queryWrapper.orderByAsc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle,Article::getCreateDate);
        queryWrapper.last("limit "+limit);
        List<Article> articles=articleMapper.selectList(queryWrapper);
        return Result.success(articles);
    }



    /*
        分页查询 article表
         */
//    @Override
//    public Result listArticle(PageParams pageParams) {
//
////        分页器
//        Page<Article> page=new Page<>(pageParams.getPage(), pageParams.getPageSize());
//
////        mybatis-plus内置的查询条件生成对象。
//        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
////        如果类别id不为空，加上类别id做筛选
//        if(pageParams.getCategoryId()!=null){
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
////        这里有些小麻烦，因为tagid不在文章表中
//        if(pageParams.getTagId()!=null){
////            先查出这个标签对应的文章id有哪些
//            LambdaQueryWrapper<ArticleTag> articleLambdaQueryWrapper=new LambdaQueryWrapper<>();
//            articleLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags=articleTagMapper.selectList(articleLambdaQueryWrapper);
//            List<Long> articleIdList=new ArrayList<>();
//            for (ArticleTag articleTag:articleTags){
//                articleIdList.add(articleTag.getArticleId());
//            }
////            将文章id加入到查询条件
//            if(articleIdList.size()>0){
////                这个语句有点方便：将符合条件的id放入查询条件
//                queryWrapper.in(Article::getId,articleIdList);
//            }
//
//        }
//        //    置顶排序：article.weight为1的文章置顶
//        queryWrapper.orderByDesc(Article::getWeight);
////        按照创建事件降序排序
//        queryWrapper.orderByDesc(Article::getCreateDate);
//
////        拿到对应页面的数据
//        Page<Article> articlePage=articleMapper.selectPage(page,queryWrapper);
//        List<Article> records=articlePage.getRecords();
//
////        数据库映射出来的pojo对象跟前端页面不匹配，需要创建一个vo对象。将对应的pojo转换为vo。
//        List<ArticleVo> articleVoList=copyList(records,true,true,false,false);
//        return Result.success(articleVoList);
//
//
//    }

    /*
      分页查询 article表，用映射文件实现
     */
    @Override
    public Result listArticle(PageParams pageParams) {


        Page<Article> page=new Page<>(pageParams.getPage(), pageParams.getPageSize());

        IPage<Article> articleIPage=articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(), pageParams.getMonth());
        List<Article> records=articleIPage.getRecords();

        return Result.success(copyList(records,true,true,false,false));




    }





    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record:records){
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }


    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo=new ArticleVo();
//        将pojo的属性复制到vo
        BeanUtils.copyProperties(article,articleVo);
//        日期类型不一致，复制不过来
        try {
            articleVo.setCreateDate(TimeUtils.stampToTime(article.getCreateDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        将作者与标签信息写进去
        if(isTag){
            Long articleId=article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId=article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickName());
        }

        if(isBody){
//            从文章类中得到bodyid
            Long bodyId=article.getBodyId();
//            再从articlebody表中拿数据
           articleVo.setBody(articleBodyService.findArticleBodyById(bodyId));
        }
        if(isCategory){
//            从文章类中得到bodyid
            Integer categoryId=article.getCategoryId();
//            再从articlebody表中拿数据
            articleVo.setCategory(categoryService.findArticleCategorysById(categoryId));
        }

        return articleVo;

    }









}

package com.example.myadmin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.myadmin.bo.OneBo;
import com.example.myadmin.mapper.*;
import com.example.myadmin.pojo.Article;
import com.example.myadmin.pojo.Category;
import com.example.myadmin.pojo.SysUser;
import com.example.myadmin.service.ArticleService;
import com.example.myadmin.utils.TimeUtils;
import com.example.myadmin.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CategoryNumberMapper categoryNumberMapper;

    @Autowired
    private TimeNumberMapper timeNumberMapper;

    @Autowired
    private BarDataMapper barDataMapper;
    @Autowired
    private OneMapper oneMapper;

    @Override
    public Result getArtilces(Map<String, Object> map) {
//        文章的查询条件初始类
        LambdaQueryWrapper<Article> queryWrapperArtilce=new LambdaQueryWrapper<>();

//        拿到传过来的参数并解析出来
        String authorname=(String) map.get("author");
        Long authorId=null;
        if(StringUtils.isNotBlank(authorname)){
            LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getNickName,authorname);
            try {
                SysUser sysUser=sysUserMapper.selectOne(queryWrapper);
                authorId=sysUser.getId();
                queryWrapperArtilce.eq(Article::getAuthorId,authorId);
            }catch (Exception e){
                System.out.println("无数据");
            }
            }

        String categoryName=(String) map.get("category");
        Long categoryId=null;
        if(StringUtils.isNotBlank(categoryName)){
            LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(Category::getCategoryName,categoryName);
            try{
                Category category=categoryMapper.selectOne(queryWrapper);
                categoryId=category.getId();
                queryWrapperArtilce.eq(Article::getCategoryId,categoryId);
            }catch (Exception e){
                System.out.println("无数据");
            }
        }

//        根据文章查询条件拿到数据
        List<Article> articlesList=articleMapper.selectList(queryWrapperArtilce);

        List<ArticleVo> articleVoList=copyList(articlesList);

        return Result.success(articleVoList);


    }

    @Override
    public Result deleteArticleById(Long articleId) {
//        LambdaQueryWrapper<Article> queryWrapperOne=new LambdaQueryWrapper<>();
//        queryWrapperOne.eq(SysUser::getId,sysUserId);

        if(articleMapper.deleteById(articleId)==1){
            return Result.success("删除成功！！");

        };

        return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());

    }

    @Override
    public Result getPieData() {

//        返回类别的数据
//小细节：数据库表中的属性名（下划线）对应项目实体类属性名（驼峰）
        List<ArticleCategoryVo> result=categoryNumberMapper.getCategoryNumbers();
//将category_id转化为实体类id
        for(ArticleCategoryVo articleCategoryVo:result){
            try{
                String categoryName=categoryMapper.selectById(articleCategoryVo.getCategoryId()).getCategoryName();
                articleCategoryVo.setCategoryName(categoryName);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Result.success(result);



    }

    /*
        从文章表中查询数据，并按年份时间分类

     */

    @Override
    public Result getLineData() {
        List<ArticleTimeVo> result=timeNumberMapper.getTimeNumbers();
        if(result!=null){
            return Result.success(result);
        }
        return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());


    }



    @Override
    public Result getBarData() {

//        获取文章浏览量
        List<BarDataVo> result=barDataMapper.getBarData();

//        获取文章评论量
        List<OneBo> reviewResult=oneMapper.getData();

//        将文章评论量整合进来
        for (BarDataVo barDataVo:result){
            for (OneBo oneBo:reviewResult){

                if(barDataVo.getId().equals(oneBo.getId())){
                    barDataVo.setReviewCounts(oneBo.getReviewCounts());
                }

            }
            if(barDataVo.getReviewCounts()==null){
                barDataVo.setReviewCounts(0);
            }

        }

        if(result!=null){
            return Result.success(result);
        }
        return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());

    }






    private List<ArticleVo> copyList(List<Article> articlesList) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record:articlesList){
            articleVoList.add(copy(record));
        }
        return articleVoList;
    }


    private ArticleVo copy(Article article){
        ArticleVo articleVo=new ArticleVo();
//        将pojo的属性复制到vo
        BeanUtils.copyProperties(article,articleVo);

//        根据文章的类别id查找对应的类别名
        Long categoryId= Long.valueOf(article.getCategoryId());
        String categoryName=categoryMapper.selectById(categoryId).getCategoryName();
        articleVo.setCategory(categoryName);

//  根据文章的作者id到sys_user表中找作者名
        try{
            Long authorId= Long.valueOf(article.getAuthorId());
            String authorName=sysUserMapper.selectById(authorId).getNickName();
            articleVo.setAuthor(authorName);
        }catch (Exception e){
            e.printStackTrace();
        }

//        日期类型不一致，复制不过来
        try {
            articleVo.setCreateDate(TimeUtils.stampToTime(article.getCreateDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return articleVo;

    }


}

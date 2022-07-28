package com.example.blogapi_springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blogapi_springboot.dao.mapper.TagMapper;
import com.example.blogapi_springboot.dao.pojo.Category;
import com.example.blogapi_springboot.dao.pojo.Tag;
import com.example.blogapi_springboot.service.TagSerivce;
import com.example.blogapi_springboot.vo.CategoryVo;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagSerivce {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {

//       mybatis-plus无法进行多表查询

        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }


    public List<TagVo> copyList(List<Tag> tagList){

        List<TagVo> tagVoList=new ArrayList<>();
        for(Tag tag:tagList){
            tagVoList.add(copy(tag));
        }
        return tagVoList;

    }

    public TagVo copy(Tag tag){
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }


    @Override
    public Result hots(Integer limit) {

//        最热标签：在文章中出现次数最多
//拿到了tag_id
        List<Long> tagIds=tagMapper.findHotsTagIds(limit);
//        判空一下,如果为空，返回空的列表
        if(CollectionUtils.isEmpty(tagIds)){
//            这个方法主要目的就是返回一个不可变的列表，使用这个方法作为返回值就不需要再创建一个新对象，可以减少内存开销。
//            并且返回一个size为0的List，调用者不需要校验返回值是否为null，所以建议使用这个方法返回可能为空的List。
            return Result.success(Collections.emptyList());
        }

//        将tag-id转换为tag-name并返回
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);

        return Result.success(tagList);
    }

    @Override
    public Result findAllTags() {
        List<Tag> categories=tagMapper.selectList(new LambdaQueryWrapper<>());
//        将pojo转换为vo再返回给前端

        List<TagVo> categoryVoList=copyList(categories);
        return Result.success(categoryVoList);


    }

    @Override
    public Result findAllTagsDetail() {

        List<Tag> categories=tagMapper.selectList(new LambdaQueryWrapper<>());
//        将pojo转换为vo再返回给前端
//        List<TagVo> categoryVoList=copyList(categories);
        return Result.success(categories);

    }

    @Override
    public Result tagDetailById(Long id) {
        Tag tag=tagMapper.selectById(id);
        return Result.success(tag);
    }
}

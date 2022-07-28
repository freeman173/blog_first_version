package com.example.blogapi_springboot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blogapi_springboot.dao.mapper.CategoryMapper;
import com.example.blogapi_springboot.dao.pojo.Category;
import com.example.blogapi_springboot.dao.pojo.Tag;
import com.example.blogapi_springboot.service.CategoryService;
import com.example.blogapi_springboot.vo.CategoryVo;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findArticleCategorysById(Integer categoryId) {
        Category category=categoryMapper.selectById(categoryId);
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAll() {
        List<Category> categories=categoryMapper.selectList(new LambdaQueryWrapper<>());
//        将pojo转换为vo再返回给前端
        List<CategoryVo> categoryVoList=copyList(categories);
        return Result.success(categoryVoList);
    }

    @Override
    public Result findAllDetail() {

        List<Category> categories=categoryMapper.selectList(new LambdaQueryWrapper<>());
//        将pojo转换为vo再返回给前端
        List<CategoryVo> categoryVoList=copyList(categories);
        return Result.success(categoryVoList);

    }

    /*

    根据

     */

    @Override
    public Result categoryDetailById(Long id) {

        Category category=categoryMapper.selectById(id);
        return Result.success(category);
    }


    public List<CategoryVo> copyList(List<Category> categoryList){

        List<CategoryVo> categoryVoList=new ArrayList<>();
        for(Category category:categoryList){
            categoryVoList.add(copy(category));
        }
        return categoryVoList;

    }

    public CategoryVo copy(Category category){
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

}

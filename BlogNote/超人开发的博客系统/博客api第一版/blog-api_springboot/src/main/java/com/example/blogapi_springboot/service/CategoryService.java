package com.example.blogapi_springboot.service;

import com.example.blogapi_springboot.vo.CategoryVo;
import com.example.blogapi_springboot.vo.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryVo findArticleCategorysById(Integer categoryId);

    Result findAll();

//查询所有分类类别的详细信息
    Result findAllDetail();

    Result categoryDetailById(Long id);
}

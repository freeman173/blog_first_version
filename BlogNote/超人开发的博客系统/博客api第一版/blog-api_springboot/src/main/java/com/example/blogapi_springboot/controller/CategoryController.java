package com.example.blogapi_springboot.controller;


import com.example.blogapi_springboot.service.CategoryService;
import com.example.blogapi_springboot.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Result categories(){

        return categoryService.findAll();

    }


    @GetMapping("/categories/detail")
    public Result categoriesDetail(){

        return categoryService.findAllDetail();
    }



    @GetMapping("/categories/detail/{id}")
    public Result catagoryDetailById(@PathVariable("id") Long id){

        return categoryService.categoryDetailById(id);

    }



}

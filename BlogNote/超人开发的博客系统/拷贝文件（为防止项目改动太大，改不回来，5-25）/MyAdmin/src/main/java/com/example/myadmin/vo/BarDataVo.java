package com.example.myadmin.vo;

import lombok.Data;

@Data
public class BarDataVo {


    private Long id;
    private String title;
//    文章的浏览量
    private Integer viewCounts;
//    文章的评论数
    private Integer reviewCounts;

}

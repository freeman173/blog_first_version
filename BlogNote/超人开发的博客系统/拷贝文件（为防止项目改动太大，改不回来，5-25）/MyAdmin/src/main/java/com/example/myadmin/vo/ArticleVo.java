package com.example.myadmin.vo;

import lombok.Data;

@Data
public class ArticleVo {
    private Long id;
    private String title;
    private String author;
    private String category;
    private String createDate;


}

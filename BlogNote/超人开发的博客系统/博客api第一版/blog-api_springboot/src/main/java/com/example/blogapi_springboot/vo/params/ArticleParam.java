package com.example.blogapi_springboot.vo.params;


import com.example.blogapi_springboot.dao.pojo.ArticleBody;
import com.example.blogapi_springboot.vo.CategoryVo;
import com.example.blogapi_springboot.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;
    private ArticleBodyParam body;
    private CategoryVo category;
    private String summary;
    private List<TagVo> tags;
    private String title;

}

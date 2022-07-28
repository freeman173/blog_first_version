package com.example.blogapi_springboot.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ArticleBody {
    @TableId(type = IdType.AUTO)//id为数据库表中的id，且为自增类型，到时不需要赋值（框架自动帮你创建）
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;


}

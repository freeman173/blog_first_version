package com.example.blogapi_springboot.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ArticleTag {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long articleId;

    private Long tagId;

}

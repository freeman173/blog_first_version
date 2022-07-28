package com.example.myadmin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    public static final int Article_Top=1;
    public static final int Article_Common=0;

    @TableId(type = IdType.AUTO)//id为数据库表中的id，且为自增类型，到时不需要赋值（框架自动帮你创建）
    private Long id;
    private Integer commentCounts;
    private Long createDate;
    private String summary;
    private String title;
    private Integer viewCounts;
    private Integer weight=Article_Common;
    private Long authorId;
    private Long bodyId;
    private Integer categoryId;

}

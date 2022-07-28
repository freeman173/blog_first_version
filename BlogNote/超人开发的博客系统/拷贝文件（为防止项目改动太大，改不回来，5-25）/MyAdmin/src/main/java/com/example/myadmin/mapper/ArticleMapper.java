package com.example.myadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myadmin.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {


}

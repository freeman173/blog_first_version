package com.example.myadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myadmin.vo.ArticleCategoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryNumberMapper extends BaseMapper<ArticleCategoryVo> {

    @Select("select category_id ,count(id) AS category_id_number from ms_article group by category_id")
    List<ArticleCategoryVo> getCategoryNumbers();

}

package com.example.myadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myadmin.vo.ArticleTimeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TimeNumberMapper extends BaseMapper<ArticleTimeVo> {

    @Select("select from_unixtime(create_date/1000, '%Y-%m') as time, count(id) as numbers from ms_article group by from_unixtime(create_date/1000, '%Y-%m')")
    List<ArticleTimeVo> getTimeNumbers();
}

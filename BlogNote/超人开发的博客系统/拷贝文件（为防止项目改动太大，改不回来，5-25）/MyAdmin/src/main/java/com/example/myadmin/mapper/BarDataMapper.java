package com.example.myadmin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myadmin.vo.ArticleTimeVo;
import com.example.myadmin.vo.BarDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BarDataMapper extends BaseMapper<BarDataVo> {
    @Select("select id,title,view_counts as viewCounts from ms_article")
    List<BarDataVo> getBarData();

}

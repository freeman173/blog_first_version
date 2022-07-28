package com.example.myadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myadmin.bo.OneBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OneMapper extends BaseMapper<OneBo> {
    @Select("select article_id as id, count(id) as reviewCounts from ms_comment group by article_id")
    List<OneBo> getData();

}

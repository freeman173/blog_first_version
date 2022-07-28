package com.example.blogapi_springboot.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogapi_springboot.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TagMapper extends BaseMapper<Tag> {

//    根据文章id查询标签列表：这里新写了接口，所以需要建立mapper.xml文件
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> findHotsTagIds(Integer limit);


    List<Tag> findTagsByTagIds(List<Long> tagIds);
}

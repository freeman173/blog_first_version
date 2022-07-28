package com.example.blogapi_springboot.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;
@Data
public class CommentVo {

//    id采用了分布式的方法导致生成的值特别大，数据传到前段时会被自动省略一部分；这里采用了一个小工具：在传到
//    前端之前，将其先转为字符串。（分布式的生成id方式很不舒服，后面来解决！！）
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private UserVo author;
    private String content;
    private List<CommentVo> children;
    private String createDate;
    private Integer level;
    private UserVo toUser;

}

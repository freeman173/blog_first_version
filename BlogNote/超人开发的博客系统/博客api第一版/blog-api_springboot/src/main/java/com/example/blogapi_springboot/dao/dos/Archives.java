package com.example.blogapi_springboot.dao.dos;

import lombok.Data;

//不需要持久化直接返给前端
@Data
public class Archives {

    private Integer year;
    private Integer month;
    private Integer count;

}

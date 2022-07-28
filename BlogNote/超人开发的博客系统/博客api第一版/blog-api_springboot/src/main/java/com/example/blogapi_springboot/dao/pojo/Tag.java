package com.example.blogapi_springboot.dao.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private Long id;

    private String avatar;

    private String tagName;

}

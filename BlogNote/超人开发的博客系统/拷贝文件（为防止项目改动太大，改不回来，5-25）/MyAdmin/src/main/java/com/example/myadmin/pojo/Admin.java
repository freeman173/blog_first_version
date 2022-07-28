package com.example.myadmin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Admin {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String userName;

    private String password;

}

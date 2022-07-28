package com.example.blogapi_springboot.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    @TableId(value = "id",type =IdType.AUTO)//id为数据库表中的id，且为自增类型，到时不需要赋值（框架自动帮你创建）
    private Long id;
    private String account;
    private Integer admin;
    private String avatar;
    private Long createDate;
    private Integer deleted;
    private String email;
    private Long lastLogin;
    private String mobilePhoneNumber;
    private String nickName;
    private String password;
    private String salt;
    private String status;


}

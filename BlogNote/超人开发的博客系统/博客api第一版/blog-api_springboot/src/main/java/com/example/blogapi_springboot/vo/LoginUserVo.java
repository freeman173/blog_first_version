package com.example.blogapi_springboot.vo;


import lombok.Data;

@Data
public class LoginUserVo {
    private Long id;
    private String account;
    private String nickName;
    private String mobilePhoneNumber;
    private String avatar;


}

package com.example.blogapi_springboot.vo.params;

import lombok.Data;

@Data
public class RegisterParam {

    private String account;
    private String password;
    private String mobileNumber;
    private String nickname;

}

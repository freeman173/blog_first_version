package com.example.blogapi_springboot.vo;


//错误代码统一存放，方便使用
public enum ErrorCode {
    PARAMS_ERROR(10001,"参数有误！!"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或者密码不存在"),
    TOKEN_ERROR(10003,"Token犯法!!"),
    NO_PERMISSION(70001,"无权限访问"),
    SESSION_TIME_OUT(90001,"会话超时"),
    ACCOUNT_EXIST(10004,"用户名已经存在"),
    NO_LOGIN(90002,"未登录");


    private Integer code;
    private String msg;

    ErrorCode(Integer code,String msg){
        this.code=code;
        this.msg=msg;

    }
    public Integer getCode(){
        return code;
    }
    public void setCode(Integer code){
        this.code=code;
    }


    public String getMsg(){
        return msg;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }


}

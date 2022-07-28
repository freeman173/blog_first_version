package com.example.blogapi_springboot.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//封装一个返回结果函数
public class Result {
    private boolean success;
    private Integer code;
    private String msg;
    private Object data;

//    成功返回
    public static Result success(Object data){

        return new Result(true,200,"success",data);
    }
//
    public static Result failure(Integer code
    ,String msg){

        return new Result(false,code,msg,null);
    }

}

package com.example.blogapi_springboot.vo.params;


import lombok.Data;

@Data
public class PageParams {

    private Integer page=1;
    private Integer pageSize=10;

    private Long categoryId;
    private Long tagId;

    private String year;
    private String authorName;
    private String month;


    public String getMonth(){
//        将 ‘1’ 变成‘01’
        if(this.month!=null && this.month.length()==1){
            return "0"+this.month;
        }
        return this.month;
    }



}

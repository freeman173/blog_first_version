package com.example.blogapi_springboot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration//配置类专用注解
@EnableSwagger2//开启swagger2
public class SwaggerConfig {
//    分组：多个Docket即可。
    @Bean
    public Docket docket1(){

        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }
    @Bean
    public Docket docket2(){

        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }
    @Bean
    public Docket docket3(){

        return new Docket(DocumentationType.SWAGGER_2).groupName("C");
    }


    //配置了swagger的Docket实例bean
    @Bean
    public Docket docket(){
//        将下面配置的apiinfo穿进去
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                //配置swagger开关与扫描接口
                .enable(true)
                .select()
                //扫描这下面的包并生成对应接口文档
                .apis(RequestHandlerSelectors.basePackage("com.example.blogapi_springboot.controller"))
                //选择指定路径的接口展示
                //.paths(PathSelectors.ant("/kunang/**"))
                .build();

    }



    //配置swagger信息apiInfo
    private ApiInfo apiInfo(){
        Contact contact=new Contact("超人","https://www.baidu.com","128823812321@qq.com");
        return new ApiInfo("超人的api文档",
                "我是超哥！！",
                "dfd",
                "d",
                contact,
                "dd",
                "dfd",
                new ArrayList<>());
    }


}

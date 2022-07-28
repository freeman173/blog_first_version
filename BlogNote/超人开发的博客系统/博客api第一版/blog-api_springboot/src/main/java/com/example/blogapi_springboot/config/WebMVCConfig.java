package com.example.blogapi_springboot.config;

import com.example.blogapi_springboot.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        跨域配置，允许“http://localhost:8080”来访问本服务
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");

    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        加入拦截路径“test”测一下
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/users/currentUser")
                .addPathPatterns("/articles/publish")
                .addPathPatterns("/myarticles")
//                登录之后才能评论
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/Admin/**");


    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //上传的图片在D盘下的OTA目录下，访问路径如：http://localhost:8081/Admin/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
        //其中OTA表示访问的前缀。"file:"是文件真实的存储路径
        registry.addResourceHandler("/Admin/**").addResourceLocations("file:D:/Admin/images/");
    }

}

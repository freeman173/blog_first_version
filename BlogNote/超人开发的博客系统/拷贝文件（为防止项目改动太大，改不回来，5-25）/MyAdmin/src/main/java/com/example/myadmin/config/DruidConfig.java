package com.example.myadmin.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DruidConfig {

//将配置文件中的属性注入到该类
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){

        return new DruidDataSource();
    }



//    后台监控页面类
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean=new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");


//        后台登陆的账号密码配置
        HashMap<String,String> initParameters=new HashMap<>();

//        增加配置
        initParameters.put("loginUsername","admin");
        initParameters.put("loginPassword","123456");

//        谁能访问
        initParameters.put("allow","");


        bean.setInitParameters(initParameters);
        return bean;

    }
//    filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean=new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

//        过滤的请求
        Map<String,String> initParameters=new HashMap<>();

//        不过滤的
        initParameters.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParameters);

        return bean;
    }



}

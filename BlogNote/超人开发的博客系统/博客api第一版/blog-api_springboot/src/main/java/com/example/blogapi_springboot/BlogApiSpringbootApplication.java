package com.example.blogapi_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi//因为访问不了swagger-ui界面，所以添加的标记
@SpringBootApplication
public class BlogApiSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApiSpringbootApplication.class, args);
    }

}

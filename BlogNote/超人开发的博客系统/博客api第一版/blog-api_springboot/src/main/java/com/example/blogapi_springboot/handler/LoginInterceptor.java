package com.example.blogapi_springboot.handler;

import com.alibaba.fastjson.JSON;
import com.example.blogapi_springboot.dao.pojo.SysUser;
import com.example.blogapi_springboot.service.LoginService;
import com.example.blogapi_springboot.utils.UserThreadLocal;
import com.example.blogapi_springboot.vo.ErrorCode;
import com.example.blogapi_springboot.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;
    @Override
//    该方法在调用controller之前执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
        1、首先判断请求路径是否为controller方法
        2、再判断token是否为空，若不为空再做校验
        3、认证通过即可放行
         */

//      handler代表请求路径的方法！！
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        String token=request.getHeader("Authorization");
//        输出一个日志信息，方便调试
        log.info("=============request start===============");
        log.info("request_uri:{}", request.getRequestURI());
        log.info("request_method:{}",request.getMethod());
        log.info("token:{}",token);
        log.info("=============request end===============");


//        token判空
        if(StringUtils.isBlank(token)){
            Result result=Result.failure(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
//        token校验
        SysUser sysUser=loginService.checkToken(token);
        if(sysUser==null){
            Result result=Result.failure(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

//        最终放行
//        我希望在controller中，直接获取用户信息 如何获取？
        UserThreadLocal.put(sysUser);
        return true;
    }


    @Override
//    任务结束之后的扫尾工作
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

//        清空userthreadlocal，避免内存占用
        UserThreadLocal.remove();

    }
}

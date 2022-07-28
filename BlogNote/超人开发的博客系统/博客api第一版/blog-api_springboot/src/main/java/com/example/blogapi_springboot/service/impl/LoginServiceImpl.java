package com.example.blogapi_springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.blogapi_springboot.dao.pojo.SysUser;
import com.example.blogapi_springboot.service.LoginService;
import com.example.blogapi_springboot.service.SysUserService;
import com.example.blogapi_springboot.utils.JWTUtils;
import com.example.blogapi_springboot.vo.ErrorCode;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
//    加密盐，用户表密码加密时需要用到,在这里写死！！
    private static final String slat="mszlu!@#";
    
    @Override
    public Result login(LoginParam loginParam) {
        /*
        1、检查参数是否合法
        2、根据用户名和密码去用户表中查询（）如果不存在，登录失败
        3、如果存在，使用jwt生成token返给前端。
        4、把token放入redis中（用户可以凭借token获取资源）
         */
//        先拿到参数
        String account=loginParam.getAccount();
        String password=loginParam.getPassword();
//如果为空，返回给前端信息
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
            return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
//        在数据库表中查找用户
//        用户密码加个密
        password= DigestUtils.md5Hex(password+slat);
        SysUser sysUser=sysUserService.findUser(account,password);
        if(sysUser==null){
            return Result.failure(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
//        借助jwt生成token
        String token= JWTUtils.createToken(sysUser.getId());

//        将token放入redis
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);

//        将token给前端
        return Result.success(token);
    }



    @Override
    public SysUser checkToken(String token) {
        //        判空
        if(StringUtils.isBlank(token)){
            return null;
        }
//        使用jwt工具校验token是否有误
        Map<String,Object> stringObjectMap=JWTUtils.checkToken(token);
        if(stringObjectMap==null){
            return null;
        }
//        去redis中找数据
        String userJson=redisTemplate.opsForValue().get("TOKEN_"+token);
//        有可能为空，数据过期之类的
        if(StringUtils.isBlank(userJson)){
            return null;
        }

//        将数据从字符串转化为对象
        SysUser sysUser=JSON.parseObject(userJson,SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {

//        直接在redis中删除信息就行
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
}

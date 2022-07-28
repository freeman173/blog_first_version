package com.example.blogapi_springboot.service.impl;


import com.alibaba.fastjson.JSON;
import com.example.blogapi_springboot.dao.pojo.SysUser;
import com.example.blogapi_springboot.service.RegisterService;
import com.example.blogapi_springboot.service.SysUserService;
import com.example.blogapi_springboot.utils.JWTUtils;
import com.example.blogapi_springboot.vo.ErrorCode;
import com.example.blogapi_springboot.vo.Result;
import com.example.blogapi_springboot.vo.params.RegisterParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service

public class RegisterServiceImpl implements RegisterService {
    //    加密盐，用户表密码加密时需要用到,在这里写死！！
    private static final String slat="mszlu!@#";
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private SysUserService sysUserService;
    /*
    注册的逻辑：
        1、判断参数是否合法
        2、判断账户是否存在，若不存在则注册
        3、生成token并存入redis
        注：加上事务，回滚机制。
     */
    @Override
    public Result register(RegisterParam registerParam) {

        String account=registerParam.getAccount();
        String password=registerParam.getPassword();
        String nickName=registerParam.getNickname();
        String mobileNumber= registerParam.getMobileNumber();
//        判空
        if (StringUtils.isBlank(account)||StringUtils.isBlank(password)||StringUtils.isBlank(nickName)){

            return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
//        判断数据库是否已经注册用户信息
        SysUser sysUser=sysUserService.findUserByAccount(account);
        if(sysUser!=null){
            return Result.failure(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }

//        走到这一步，说明用户还未注册
//        给对象设置值，这里有些繁琐了，后面回来优化！
        sysUser=new SysUser();
        sysUser.setAccount(account);
        sysUser.setNickName(nickName);
        sysUser.setMobilePhoneNumber(mobileNumber);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
//        直接设置当前的时间戳
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());

//        随机选择头像
        Random rand = new Random();
        Integer a= rand.nextInt(6)+1;
        sysUser.setAvatar("/static/user/"+Integer.toString(a)+".png");
        sysUser.setAdmin(1);
        sysUser.setDeleted(0);
        sysUser.setSalt(slat);
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
//       生成token并存入redis
        //        借助jwt生成token
        String token= JWTUtils.createToken(sysUser.getId());

//        将token与序列化后的对象以键值对的方式放入redis
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);

//        将token给前端
        return Result.success(token);
    }
}

package com.example.blogapi_springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blogapi_springboot.dao.mapper.SysUserMapper;
import com.example.blogapi_springboot.dao.pojo.SysUser;
import com.example.blogapi_springboot.dao.pojo.Tag;
import com.example.blogapi_springboot.service.LoginService;
import com.example.blogapi_springboot.service.SysUserService;
import com.example.blogapi_springboot.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser= sysUserMapper.selectById(id);
        UserVo userVo=new UserVo();
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickName("超人");
        }
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser= sysUserMapper.selectById(id);

        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setNickName("超人");
        }
        return sysUser;
    }


    @Override
    public SysUser findUser(String account, String password) {

//        查询条件
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickName,SysUser::getMobilePhoneNumber);
        queryWrapper.last("limit 1");

//        返回查询结果
        return sysUserMapper.selectOne(queryWrapper);

    }

    public LoginUserVo copy(SysUser sysUser){
        LoginUserVo loginUserVo=new LoginUserVo();
        BeanUtils.copyProperties(sysUser,loginUserVo);
        return loginUserVo;
    }

    @Override
    /*
    1、token是否合法（若失败，返回失败信息）
    2、如果成功，返回用户信息给前端
     */
    public Result findUserByToken(String token) {

//        与redis中的数据库对比
        SysUser sysUser=loginService.checkToken(token);
        if(sysUser==null){
            return Result.failure(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
//将查询的user转化为loginvo
        LoginUserVo loginUserVo=copy(sysUser);

        return Result.success(loginUserVo);

    }

    @Override
    public void save(SysUser sysUser) {

        sysUserMapper.insert(sysUser);
    }

    @Override
    public SysUser findUserByAccount(String account) {

        //        查询条件
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");

//        返回查询结果
        return sysUserMapper.selectOne(queryWrapper);

    }

    public SysUser findUserByName(String name) {

        //        查询条件
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getNickName,name);

//        返回查询结果
        return sysUserMapper.selectOne(queryWrapper);

    }


}

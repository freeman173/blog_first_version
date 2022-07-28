package com.example.myadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.myadmin.mapper.SysUserMapper;
import com.example.myadmin.pojo.SysUser;
import com.example.myadmin.service.UserService;
import com.example.myadmin.utils.TimeUtils;
import com.example.myadmin.vo.ErrorCode;
import com.example.myadmin.vo.Result;
import com.example.myadmin.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    SysUserMapper sysUserMapper;


    @Override
    public Result getSysUsers(Map<String, Object> map) {

        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        String name=(String) map.get("name");
        String mobileNumber= (String) map.get("mobileNumber");

        if(StringUtils.isNotBlank(name)){
            queryWrapper.eq(SysUser::getNickName,name);
        }
        if(StringUtils.isNotBlank(mobileNumber)){
            queryWrapper.eq(SysUser::getMobilePhoneNumber,mobileNumber);
        }


        List<SysUser> sysUserList=sysUserMapper.selectList(queryWrapper);

        List<UserVo> userVoList=copyList(sysUserList);

        return Result.success(userVoList);
    }

    @Override
    public Result editSysUser(SysUser sysUser) {

        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
//     更新条件
        queryWrapper.eq(SysUser::getId,sysUser.getId());
//        执行更新操作
        sysUserMapper.update(sysUser,queryWrapper);

//        再重新查询
        LambdaQueryWrapper<SysUser> queryWrapperOne=new LambdaQueryWrapper<>();
        queryWrapperOne.eq(SysUser::getId,sysUser.getId());
        SysUser sysUser1=sysUserMapper.selectOne(queryWrapperOne);

//        转化为Uservo并返回
        return Result.success(copy(sysUser1));



    }

    @Override
    public Result deleteSysUserById(Long sysUserId) {
        LambdaQueryWrapper<SysUser> queryWrapperOne=new LambdaQueryWrapper<>();
//        queryWrapperOne.eq(SysUser::getId,sysUserId);

        if(sysUserMapper.deleteById(sysUserId)==1){
            return Result.success("删除成功！！");

        };

        return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
    }

    private List<UserVo> copyList(List<SysUser> sysUserList) {
        List<UserVo> articleVoList=new ArrayList<>();
        for (SysUser record:sysUserList){
            articleVoList.add(copy(record));
        }
        return articleVoList;
    }


    private UserVo copy(SysUser article){
        UserVo articleVo=new UserVo();
//        将pojo的属性复制到vo
        BeanUtils.copyProperties(article,articleVo);
//        日期类型不一致，复制不过来
        try {
            articleVo.setCreateDate(TimeUtils.stampToTime(article.getCreateDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return articleVo;

    }


}

//package com.example.myadmin.config;
//
//
//import com.example.springboot5_springboot_shiro.pojo.User;
//import com.example.springboot5_springboot_shiro.service.UserServiceImpl;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class UserRealm extends AuthorizingRealm {
//    @Autowired
//    UserServiceImpl userService;
////    授权
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//
//        System.out.println("执行了=》授权doGetAuthorizationInfo");
//
////        给路过的用户添加权限，让用户可以访问对应权限的页面
//        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
//
//
////        拿到当前登录的对象
//        Subject subject= SecurityUtils.getSubject();
////        user对象从下面的认证函数传过来，这里来接收
//        User user=(User)subject.getPrincipal();
////        给路过的用户添加权限，让用户可以访问对应权限的页面（每个用户都有自己的权限！！）
//        info.addStringPermission(user.getPerms());
//        return info;
//
//
//    }
//
////    认证
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        System.out.println("执行了=》认证doGetAuthorizationInfo");
//
////        拿到前端传过来的用户账号
//        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken) authenticationToken;
////        放入数据库中比对看是否有此人
//        User user=userService.queryuserbyname(usernamePasswordToken.getUsername());
//    //账号名校验
//        if (user==null){//如果没有此人
//            return null;//如果用户名不匹配，就抛出“UnknownAccountException”异常。
//        }
//
////        设置shiro中的session,以备前端页面做判断
//        Subject currentsubject=SecurityUtils.getSubject();
//        Session session=currentsubject.getSession();
//        session.setAttribute("loginuser",user);
//
//
//
////        密码校验交给shiro做：
////        在这一步：shiro给用户密码做了MD5加密，下次可以通过调试来简单查看一下。
//        return new SimpleAuthenticationInfo(user,user.getPwd(),"");
//
//
//    }
//}
//
//
//
//

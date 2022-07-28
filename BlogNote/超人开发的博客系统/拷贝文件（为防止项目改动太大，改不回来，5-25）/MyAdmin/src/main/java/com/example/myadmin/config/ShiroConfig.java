//package com.example.myadmin.config;
//
//import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Configuration
//public class ShiroConfig {
//
//    //三个配置类环环相扣
//
//    //该类用于管理securityManager
//    @Bean
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
//
//        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
//        bean.setSecurityManager(securityManager);
////        添加内置过滤器
//        /*
//        anon:无需认证就可访问；
//        authc：认证了才能访问；
//        user：必须拥有记住我的功能才能用
//        perms：拥有对某个资源的权限才能访问
//        role：拥有某个角色的权限才能访问
//         */
//
////        将一些页面加入到过滤器；注意路径问题（记得在最前面加"/"）。
//        Map<String,String> filtermap=new LinkedHashMap<>();
////        被授权了才能访问以下页面:
//        filtermap.put("/user/add","perms[user:add]");
//        filtermap.put("/user/update","perms[user:update]");
//
//
////        认证访问
//        filtermap.put("/user/*","authc");
//        bean.setFilterChainDefinitionMap(filtermap);
////        设置登录页面的访问路径（如果访问某些页面时被拦截，就会自动跳到登录页面）
//        bean.setLoginUrl("/tologin");
//        //        设置未授权提示页面的访问路径（如果访问某些页面时未授权，就会自动跳到该页面）
//        bean.setUnauthorizedUrl("/noauth");
//
//
//        return bean;
//    }
//
//
//    //该类用于管理UserRealm;@Qualifier(“userRealm”)可以指定注入的具体对象；
//    //bean的“name”属性：bean名称，如果不写会默认为注解的方法名称。
//    @Bean(name = "securityManager")
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
//
//
//        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
////        将UserRealm放入securityManager中！！
//        securityManager.setRealm(userRealm);
//
//        return securityManager;
//    }
//
//    @Bean//创建realm对象
//    public UserRealm userRealm(){return new UserRealm();}
//
//
//    @Bean//用来整合shiro—thymeleaf
//    public ShiroDialect getShiroDialect(){
//        return new ShiroDialect();
//    }
//
//
//}

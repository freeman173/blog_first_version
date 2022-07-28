package com.example.blogapi_springboot.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//jwt工具类
public class JWTUtils {
    private static final String jwtToken="123456Mszlu!@#$$";

    public static String createToken(Long userId){
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder= Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,jwtToken)
                .setClaims(claims)//body数据，保证唯一性
                .setIssuedAt(new Date())//设置签发时间
                .setExpiration(new Date(System.currentTimeMillis()+24*60*60*1000));//一天有效时间

        String token=jwtBuilder.compact();
        return token;
    }


    public static Map<String,Object> checkToken(String token){
        try{
            Jwt parse =Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String,Object>) parse.getBody();
        }catch (Exception exception)
        {exception.printStackTrace();}
        return null;
    }





}

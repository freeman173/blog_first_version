package com.example.blogapi_springboot;

import com.example.blogapi_springboot.dao.dos.Archives;
import com.example.blogapi_springboot.dao.mapper.ArticleMapper;
import com.example.blogapi_springboot.dao.mapper.TagMapper;
import com.example.blogapi_springboot.dao.pojo.Article;
import com.example.blogapi_springboot.dao.pojo.Tag;
import com.example.blogapi_springboot.service.ArticleBodyService;
import com.example.blogapi_springboot.service.CategoryService;
import com.example.blogapi_springboot.service.impl.ArticleServiceImpl;
import com.example.blogapi_springboot.utils.JWTUtils;
import com.example.blogapi_springboot.utils.TimeUtils;
import com.example.blogapi_springboot.vo.ArticleBodyVo;
import com.example.blogapi_springboot.vo.CategoryVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootTest
class BlogApiSpringbootApplicationTests {

//    @Autowired
//    private TagMapper tagMapper;
    @Autowired
    private ArticleMapper articleMapper;

//    CategoryService categoryService;
    @Test
    void contextLoads() throws Exception {
//        List<Long> list=new ArrayList<Long>();
//        list.add(5L);
//        list.add(7L);
//        list.add(8L);
//        List<Tag> list1=tagMapper.findTagsByTagIds(list);


//        List<Archives> result=articleMapper.listArchives();
//        String token= JWTUtils.createToken(100L);
//        System.out.println(token);
//        Map<String,Object> map=JWTUtils.checkToken(token);
//        System.out.println(map.get("userId"));
//
//        String password= DigestUtils.md5Hex("adminmszlu!@#");

        //rand.nextInt(100) 获得的值是区间 [0, 99]，那么在这个区间左右各加 1，就得到了区间 [1, 100]
//        Long a=1523947720727l;
//        String date=TimeUtils.stampToTime(a);

        List<Archives> archivesList=articleMapper.myListArchives(5l);

        System.out.println("Ok");

    }

}

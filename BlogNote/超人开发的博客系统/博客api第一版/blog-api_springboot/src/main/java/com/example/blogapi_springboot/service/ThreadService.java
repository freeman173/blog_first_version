package com.example.blogapi_springboot.service;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.blogapi_springboot.dao.mapper.ArticleMapper;
import com.example.blogapi_springboot.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

//这个操作需要在线程池里面执行，不能影响原有的主进程
    @Async("taskExecuter")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        Integer viewCounts=article.getViewCounts();
        Article articleUpdate=new Article();
        articleUpdate.setViewCounts(viewCounts+1);
        LambdaUpdateWrapper<Article> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
//  下面这行代码是为了确保多线程执行安全
        updateWrapper.eq(Article::getViewCounts,viewCounts);
//        这里需要传入一个对象才能更新，不很很懂它的原理，后面有需要再来了解它！！
        articleMapper.update(articleUpdate,updateWrapper);

//        try{
//            Thread.sleep(5000);
//            System.out.println("更新完成");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }
}

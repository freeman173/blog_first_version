package com.example.blogapi_springboot.config;


import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync//开启异步操作:，就是注解@EnableAsync就可以使用多线程，@Async加在线程任务的方法上（需要异步执行的任务）
public class ThreadPoolConfig {
    @Bean("taskExecuter")
    public Executor asynServiceExcuter(){

//        创建并配置
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
//        核心线程数为5
        executor.setCorePoolSize(5);
//        最大线程数为20
        executor.setMaxPoolSize(20);
//        配置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
//        线程活跃时间:允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁.
        executor.setKeepAliveSeconds(60);
//        默认线程名称
        executor.setThreadNamePrefix("超人的项目");
//       所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);

//      配置好了返回
        executor.initialize();
        return executor;
    }


}

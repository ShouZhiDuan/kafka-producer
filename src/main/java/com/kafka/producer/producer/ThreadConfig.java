package com.kafka.producer.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;


/**
 * @Auther: ShouZhi@Duan
 * @Description: 线程池配置
 */
@Slf4j
@Configuration
@EnableAsync
public class ThreadConfig implements AsyncConfigurer {
    /**
     * 核心线程树
     */
    @Value("${thread.config.corePoolSize}")
    private Integer corePoolSize;
    /**
     * 最大线程池数量
     */
    @Value("${thread.config.maxPoolSize}")
    private Integer maxPoolSize;
    /**
     * 队列长度
     */
    @Value("${thread.config.queueCapacity}")
    private Integer queueCapacity;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        log.info("[多线程配置初始化]，最大核心线程数:{}，最大线程池数量:{},线程处理队列长度:{}", corePoolSize, maxPoolSize, queueCapacity);
        //核心线程数
        executor.setCorePoolSize(corePoolSize);
        //最大线程池数量
        executor.setMaxPoolSize(maxPoolSize);
        //线程处理队列长度
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        log.info("-----------多线程异常handler------------");
        return new SpringAsyncExceptionHandler();
    }

    class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            log.error("Asyn返回异常：" + ex.getCause().getMessage() + method.getName());
        }
    }
}

/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/24 下午10:30
 *
 *
 */
package com.feng.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO 
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/24 下午10:30
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/24 下午10:30
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService getThreadPool(){
        return Executors.newFixedThreadPool(100);
    }
}

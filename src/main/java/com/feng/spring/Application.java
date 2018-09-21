package com.feng.spring;

import com.feng.spring.entity.Constans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SpringBootApplication(scanBasePackages = {"com.feng.spring"})
@ComponentScan("com.feng.spring")
@EnableCaching
@EnableTransactionManagement
public class Application {

    protected final static Logger logger=LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        logger.info(Constans.print());
    }
}

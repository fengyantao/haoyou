package com.feng.spring.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * 资源管理器
 *
 * @author: fengyantao
 * @date: 2020/5/16 下午2:21
 * @version: V1.0
 * @review: fengyantao/2020/5/16 下午2:21
 */
@Configuration
@Slf4j
public class WebSourceConfiguration implements WebMvcConfigurer {

    @Value("${static.path}")
    private String qrPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = StringUtils.join(new String[]{"file:///" + qrPath, ""}, File.separator);
        log.info("|磁盘映射|公共静态资源|映射地址[{}]", path);
        registry.addResourceHandler("/fytResource/**").addResourceLocations(path);
    }
}

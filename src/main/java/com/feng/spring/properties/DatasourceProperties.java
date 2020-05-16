package com.feng.spring.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库配置
 *
 * @author: fengyantao
 * @date: 2020/5/16 下午2:05
 * @version: V1.0
 * @review: fengyantao/2020/5/16 下午2:05
 */
@ConfigurationProperties(prefix = "spring.datasource")
@Component
@Data
public class DatasourceProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
}

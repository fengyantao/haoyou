/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/22 上午11:20
 *
 *
 */
package com.feng.spring.config;

import com.feng.spring.interceptor.UserSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/22 上午11:20
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/22 上午11:20
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludes=new String[]{"/static/**","/qr/get/img","/qr/login"};
        registry.addInterceptor(permissionInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
    }

    @Bean
    public UserSecurityInterceptor permissionInterceptor() {
        return new UserSecurityInterceptor();
    }


}

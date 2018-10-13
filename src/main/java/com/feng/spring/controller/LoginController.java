/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/29 下午11:17
 *
 *
 */
package com.feng.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO 
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/29 下午11:17
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/29 下午11:17
 */
@Controller
@RequestMapping("/")
@Slf4j
public class LoginController {

    @GetMapping
    public String index(HttpServletRequest request, HttpServletResponse response) {
        log.info("sessionId[{}]|LoginController|访问首页|请求头[{}]", request.getSession().getId(),request.getHeader("User-Agent"));
        return "page/index";
    }
}

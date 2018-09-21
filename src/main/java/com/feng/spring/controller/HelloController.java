/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: fengyantao[feng_yt@suixingpay.com]
 * @date: 2018/9/21 下午4:01
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.feng.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author: fengyantao[feng_yt@suixingpay.com]
 * @date: 2018/9/21 下午4:01
 * @version: V1.0
 * @review: fengyantao[feng_yt@suixingpay.com]/2018/9/21 下午4:01
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/index")
    public String index(){
        return "HelloWorld";
    }
}

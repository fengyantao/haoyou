/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/22 上午10:15
 *
 *
 */
package com.feng.spring.controller;

import com.alibaba.fastjson.JSONObject;
import com.feng.spring.entity.ChartVo;
import com.feng.spring.service.QrCodeService;
import com.feng.spring.utils.ReqUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二维码登录类
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/22 上午10:15
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/22 上午10:15
 */
@Controller
@RequestMapping("/qr")
@Slf4j
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    /**
     *1.页面单击生成登录按钮，后台接收请求，生成二维码图片响应给前台展示
     * 1.1 前台展示生成的图片，并接收后台的uuid
     * 1.2 前台每2s发送一次校验登录请求，查看当前uuid是否登录
     * 1.3 生成二维码图片前，将cookieid和qrUuid建立关系，存储在redis中。Core实例化存储在redis中
     *
     *2. 根据cookieId_bean获取core实体，并生成对应的好友分析图
     *
     *
     */

    /**
     * 首页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        log.info("sessionId[{}]|QrCodeController|访问首页|请求头[{}]", request.getSession().getId(),request.getHeader("User-Agent"));
        return "page/index";
    }

    /**
     * 登录页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        log.info("sessionId[{}]|访问登录页", request.getSession().getId());
        String ua = request.getHeader("User-Agent");
        if (ReqUtils.checkAgentIsMobile(request)){
            log.info("sessionId[{}]|手机访问登录页|请求头[{}]|不能登录", request.getSession().getId(),ua);
            return "page/nologin";
        }else {
            log.info("sessionId[{}]|电脑访问登录页|请求头[{}]|可以登录", request.getSession().getId(),ua);
            return "page/login";
        }

    }

    /**
     * 目录页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/menu")
    public String menu(HttpServletRequest request, HttpServletResponse response) {
        log.info("sessionId[{}]|访问目录页", request.getSession().getId());
        return "page/menu";
    }

    /**
     * 性别比例图
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/sex")
    public String sex(HttpServletRequest request, HttpServletResponse response) {
        log.info("sessionId[{}]|访问性别比例图", request.getSession().getId());
        return "page/sex";
    }

    /**
     * 省份图
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/province")
    public String province(HttpServletRequest request, HttpServletResponse response) {
        log.info("sessionId[{}]|访问省份图", request.getSession().getId());
        return "page/province";
    }

    /**
     * 签名分布图
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/sign")
    public String sign(HttpServletRequest request, HttpServletResponse response) {
        log.info("sessionId[{}]|访问签名分布图", request.getSession().getId());
        return "page/sign";
    }

    /**
     * 校验是否登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/check/login")
    @ResponseBody
    public String checkLogin(HttpServletRequest request, HttpServletResponse response) {

        log.info("sessionId[{}]|校验微信是否登录,uuid:[{}]", request.getSession().getId(), request.getParameter("qrId"));

        if (qrCodeService.checkLogin(request.getSession().getId())) {

            return "1";
        }

        return "2";
    }

    /**
     * 获得二维码id
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/get/qrId")
    @ResponseBody
    public String getQrId(HttpServletRequest request, HttpServletResponse response) {

        log.info("sessionId[{}]|获取二维码ID", request.getSession().getId());

        String qrId = qrCodeService.getQrId(request.getSession().getId());


        log.info("sessionId[{}]|获取二维码ID|数据[{}]|END", request.getSession().getId(),qrId);

        return "1";

    }

    /**
     * 获得二维码图片
     *
     * @param request
     * @param response
     */
    @RequestMapping("/get/img")
    public void getImg(HttpServletRequest request, HttpServletResponse response) {

        String sessionId = request.getSession().getId();
        log.info("sessionId[{}]|获取二维码图片", sessionId);
        qrCodeService.getQrImg(sessionId, response);

    }

    /**
     * 获取性别比例图
     *
     * @param request
     * @param response
     */
    @RequestMapping("/get/sex")
    @ResponseBody
    public Map<String, Object> getSex(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getSession().getId();
        Map<String, Object> map = new HashMap<>();
        log.info("sessionId[{}]|获取性别比例图", sessionId);

        //性别比例的list
        List<ChartVo> seriesData = qrCodeService.getSex(sessionId);

        List<String> legendData = Arrays.asList(new String[]{"男", "女", "未知"});

        map.put("legendData", legendData);
        map.put("seriesData", seriesData);
        log.info("sessionId[{}]|获取性别比例图|数据[{}]|END", sessionId, JSONObject.toJSONString(seriesData));
        return map;
    }


    /**
     * 获取省份图
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/get/province")
    @ResponseBody
    public Map<String, Object> getProvince(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getSession().getId();
        log.info("sessionId[{}]|获取省份图", sessionId);

        Map<String, Object> province = qrCodeService.getProvince(sessionId);

        log.info("sessionId[{}]|获取省份图|数据[{}]|END", sessionId, JSONObject.toJSONString(province));

        return province;
    }

    @RequestMapping("/get/sign")
    @ResponseBody
    public List<ChartVo> getSign(HttpServletRequest request, HttpServletResponse response){
        String sessionId = request.getSession().getId();

        log.info("sessionId[{}]|获取签名分布图", sessionId);

        List<ChartVo> signList = qrCodeService.getSign(sessionId);
        String result = JSONObject.toJSONString(signList);
        log.info("sessionId[{}]|获取签名分布图|数据[{}]|END", sessionId,result);

        return signList;
    }
}

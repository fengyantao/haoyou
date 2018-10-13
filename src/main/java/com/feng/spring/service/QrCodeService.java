/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/22 下午9:24
 *
 *
 */
package com.feng.spring.service;

import cn.zhouyafeng.itchat4j.core.Core;
import com.feng.spring.entity.ChartVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/22 下午9:24
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/22 下午9:24
 */
public interface QrCodeService {

    /**
     * 获得二维码图片
     * @param paramId sessionId
     * @param response
     */
    void getQrImg(String paramId, HttpServletResponse response);

    /**
     * 获得二维码id
     * @param paramId
     * @return
     */
    String getQrId(String paramId);

    /**
     * 校验是否登录
     * @param paramId
     * @return
     */
    boolean checkLogin(String paramId);


    /**
     * 操作redis，设置sessionId，二维码id
     * @param paramId
     * @param uuid
     * @param core
     */
    void setBeanAndIdInRedis(String paramId, String uuid, Core core);


    /**
     * 获取性别比例图
     * @param paramId
     * @return
     */
    List<ChartVo> getSex(String paramId);

    /**
     * 获取省份图
     * @param paramId
     * @return
     */
    Map<String,Object> getProvince(String paramId);


    /**
     * 获取签名分布图
     * @param paramId
     * @return
     */
    List<ChartVo> getSign(String paramId);
}

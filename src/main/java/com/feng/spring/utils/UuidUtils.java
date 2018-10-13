/**
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018年6月4日 上午10:28:12
 *
 *
 */
package com.feng.spring.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * uuid工具类
 * @author: feng[17316085657@163.com]
 * @date: 2018年6月4日 上午10:28:12
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018年6月4日 上午10:28:12
 */
public class UuidUtils {

    public static String get32Uuid(){
        return UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);
    }

    /**
     *
     * 获取64位主键
     * @return
     */
    public static String get64Uuid() {
        // 15位数字数组
        int[] number = new int[15];
        // 循环变量
        int i = 0;
        StringBuffer bussinessNo = new StringBuffer(64);
        bussinessNo.append(getStringDate());
        bussinessNo.append(get32Uuid());
        // 生成15位随机数算法
        for (i = 0; i < number.length; i++) {
            if (number[i] == 0) {
                // 产生0-10之间的随机小数，强制转换成正数
                number[i] = (int) (Math.random() * 10);
            }
            bussinessNo.append(number[i]);
        }
        return bussinessNo.toString();
    }

    /**
     *
     * 获取现在时间 yyyyMMddHHmmssSS(17位)
     * @return
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}

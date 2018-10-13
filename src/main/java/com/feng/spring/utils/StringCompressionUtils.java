/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/29 下午2:49
 *
 *
 */
package com.feng.spring.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringCompressionUtils {

    static String code = "UTF-8";

    /**
     * 压缩
     * @param str
     * @return
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
//        System.out.println("压缩之前的字符串大小：" + str.length());
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }

    /**
     * 解压缩
     * @param str
     * @return
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {
//        System.out.println("压缩之后的字符串大小：" + str.length());
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString();
    }

    public static void main(String[] args) throws IOException {

        System.out.println(StringCompressionUtils.compress("fdsagagdsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"));
        System.out.println(StringCompressionUtils.uncompress(StringCompressionUtils.compress("fsafgdfdsgfdgfdghfhgfhgfhdgdsfagdsgdasgddddddddddddddddddddddddd")));
    }
}

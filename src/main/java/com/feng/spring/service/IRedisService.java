/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/23 下午9:54
 *
 *
 */
package com.feng.spring.service;

import java.util.List;

/**
 * TODO
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/23 下午9:54
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/23 下午9:54
 */

public interface IRedisService {

    boolean set(String key, String value);

    boolean setEx(final String key, final long seconds, final String value);

    String get(String key);

    boolean expire(String key, long expire);

    <T> boolean setList(String key, List<T> list);

    <T> List<T> getList(String key, Class<T> clz);

    long lpush(String key, Object obj);

    long rpush(String key, Object obj);

    String lpop(String key);

}


/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/25 下午9:44
 *
 *
 */
package com.feng.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 图表实体类
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/25 下午9:44
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/25 下午9:44
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChartVo implements Serializable,Comparable<ChartVo> {
    private static final long serialVersionUID = -8906841771730235200L;

    private String name;

    private int value;

    @Override
    public int compareTo(ChartVo vo){
        return vo.getValue()-this.getValue();
    }
}

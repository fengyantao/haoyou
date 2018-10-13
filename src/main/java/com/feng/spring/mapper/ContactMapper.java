package com.feng.spring.mapper;

import com.feng.spring.entity.ChartVo;
import com.feng.spring.entity.Contact;

import java.util.List;

public interface ContactMapper {

    int deleteByPrimaryKey(String uuid);

    int insert(Contact record);

    Contact selectByPrimaryKey(String uuid);

    List<Contact> selectAll();

    int updateByPrimaryKey(Contact record);

    List<ChartVo> getSex(String uuid);

    List<ChartVo> getProvince(String uuid);

    List<Contact> getSign(String uuid);
}

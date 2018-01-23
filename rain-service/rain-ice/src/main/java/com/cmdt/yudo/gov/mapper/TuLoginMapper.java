package com.cmdt.yudo.gov.mapper;

import com.cmdt.yudo.gov.model.TuLogin;

public interface TuLoginMapper {
    int deleteByPrimaryKey(String vin);

    int insert(TuLogin record);

    int insertSelective(TuLogin record);

    TuLogin selectByPrimaryKey(String vin);

    int updateByPrimaryKeySelective(TuLogin record);

    int updateByPrimaryKey(TuLogin record);
}
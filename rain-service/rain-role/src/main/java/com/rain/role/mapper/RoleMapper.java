package com.rain.role.mapper;


import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Map record);

    int insertSelective(Map record);

    Map selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Map record);

    int updateByPrimaryKey(Map record);
}
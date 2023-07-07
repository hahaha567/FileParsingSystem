package com.hahaha.fileparsingsystem.security.dao;

import com.hahaha.fileparsingsystem.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;


@Mapper
public interface UserMapper {
    User getUserByName(String userName);
    void insert(String userName, String passWord, Timestamp createTime, Timestamp updateTime);
    Long getIdByName(String userName);
}

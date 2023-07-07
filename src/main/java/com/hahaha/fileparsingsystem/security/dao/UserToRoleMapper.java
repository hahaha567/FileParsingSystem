package com.hahaha.fileparsingsystem.security.dao;

import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;


@Mapper
public interface UserToRoleMapper {
    void insert(Long userId, Long roleId);
}

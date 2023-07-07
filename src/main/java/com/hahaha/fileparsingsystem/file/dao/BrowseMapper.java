package com.hahaha.fileparsingsystem.file.dao;

import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Mapper
public interface BrowseMapper {
    public void insert(Long userId, Long fileId, Timestamp createTime);
}

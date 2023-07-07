package com.hahaha.fileparsingsystem.file.dao;

import com.hahaha.fileparsingsystem.file.entity.FileRecord;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Mapper
public interface FileMapper {
    void insert(Long userId, String fileName, String storePath, Timestamp createTime, Timestamp updateTime);
    FileRecord getFileById(Long id);
    Long getUserIdById(Long id);
    void updateFile(Long id, Timestamp updateTime);
    void deleteFile(Long id);
}

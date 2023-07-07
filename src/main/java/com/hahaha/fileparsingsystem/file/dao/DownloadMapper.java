package com.hahaha.fileparsingsystem.file.dao;

import com.hahaha.fileparsingsystem.file.entity.Download;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Mapper
public interface DownloadMapper {
    public void insert(Download download);
}

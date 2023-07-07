package com.hahaha.fileparsingsystem.file.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FileRecord {
    private Long id;
    private Long userId;
    private String fileName;
    private String storePath;
    private Timestamp createTime;
    private Timestamp updateTime;
}

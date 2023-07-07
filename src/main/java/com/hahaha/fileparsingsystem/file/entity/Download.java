package com.hahaha.fileparsingsystem.file.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Download {
    private Long id;
    private Long userId;
    private Long fileId;
    private Timestamp createTime;
}

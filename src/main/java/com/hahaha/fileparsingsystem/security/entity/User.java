package com.hahaha.fileparsingsystem.security.entity;

import lombok.Data;


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String userName;
    private String passWord;
    private List<String> roles;
    private Timestamp creteTime;
    private Timestamp updateTime;
}

package com.hahaha.fileparsingsystem.file.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Result implements Serializable {
    private ResultCode code;
    private String message;
    private Object data;
}

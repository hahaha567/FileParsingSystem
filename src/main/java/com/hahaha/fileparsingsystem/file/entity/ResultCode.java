package com.hahaha.fileparsingsystem.file.entity;


public enum ResultCode {

    SUCCESSFUL("00000", "OK"),
    NOACCESS("A0001", "no access"),
    OPERATIONFAIL("B0001", "failed");


    private String code;
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}

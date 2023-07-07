package com.hahaha.fileparsingsystem.file.controller;

import com.hahaha.fileparsingsystem.file.entity.Result;
import com.hahaha.fileparsingsystem.file.entity.ResultCode;
import com.hahaha.fileparsingsystem.file.exception.NoAccessException;
import com.hahaha.fileparsingsystem.file.exception.OperationFailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @Description 捕获NoAccessException异常，该异常代表当前用户无权限执行此操作
     * @Date 15:58 2023/6/19
     * @Param []
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    @ExceptionHandler(NoAccessException.class)
    public Result noAccess() {
        Result result = new Result(ResultCode.NOACCESS, "当前用户无权限执行此操作", null);
        return result;
    }

    /**
     * @Description 捕获OperationFailException，该异常主要是代表操作失败
     * @Date 15:59 2023/6/19
     * @Param [exception]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    @ExceptionHandler(OperationFailException.class)
    public Result fail(OperationFailException exception) {
        Result result = new Result(ResultCode.OPERATIONFAIL, exception.getMessage(), null);
        return result;
    }

}

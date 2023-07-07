package com.hahaha.fileparsingsystem.security.controller;

import com.hahaha.fileparsingsystem.file.entity.Result;
import com.hahaha.fileparsingsystem.security.entity.User;
import com.hahaha.fileparsingsystem.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SecurityController {
    @Autowired
    UserService userService;

    /**
     * @Description 插入用户数据
     * @Date 15:51 2023/6/19
     * @Param [user]
     * @return java.lang.String
     **/
    @PostMapping("/insert")
    public Result insertUser(@RequestBody User user) {
        Result result = userService.saveUser(user);
        return result;
    }




}

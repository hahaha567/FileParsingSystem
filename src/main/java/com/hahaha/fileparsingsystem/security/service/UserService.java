package com.hahaha.fileparsingsystem.security.service;

import com.hahaha.fileparsingsystem.file.entity.Result;
import com.hahaha.fileparsingsystem.file.entity.ResultCode;
import com.hahaha.fileparsingsystem.security.dao.RoleMapper;
import com.hahaha.fileparsingsystem.security.dao.UserMapper;
import com.hahaha.fileparsingsystem.security.dao.UserToRoleMapper;
import com.hahaha.fileparsingsystem.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Transactional
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserToRoleMapper userToRoleMapper;

    /**
     * @Description 根据用户名得到用户信息
     * @Date 15:44 2023/6/19
     * @Param [userName]
     * @return com.hahaha.fileparsingsystem.security.entity.User
     **/
    public User getUserByName(String userName) {
        User user = userMapper.getUserByName(userName);
        user.setRoles(roleMapper.getRolesByUserId(user.getId()));
        return user;
    }

    /**
     * @Description 保存用户信息
     * @Date 15:45 2023/6/19
     * @Param [user]
     * @return void
     **/
    public Result saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassWord(encoder.encode(user.getPassWord()));
        Timestamp time = new Timestamp(System.currentTimeMillis());
        userMapper.insert(user.getUserName(), user.getPassWord(), time, time);
        Long userId = userMapper.getIdByName(user.getUserName());
        for (String role : user.getRoles()) {
            userToRoleMapper.insert(userId, roleMapper.getIdByName(role));
        }
        Result result = new Result(ResultCode.SUCCESSFUL, "用户保存成功", null);
        return result;
    }

    /**
     * @Description 根据用户名得到用户id
     * @Date 15:46 2023/6/19
     * @Param [userName]
     * @return java.lang.Long
     **/
    public Long getIdByName(String userName) {
        return userMapper.getIdByName(userName);
    }
}

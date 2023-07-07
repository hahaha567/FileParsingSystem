package com.hahaha.fileparsingsystem.security.dao;

import com.hahaha.fileparsingsystem.security.entity.Role;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface RoleMapper {
    List<String> getRolesByUserId(Long id);
    Long getIdByName(String name);
}

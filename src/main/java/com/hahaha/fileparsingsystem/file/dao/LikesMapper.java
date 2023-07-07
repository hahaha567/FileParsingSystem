package com.hahaha.fileparsingsystem.file.dao;

import com.hahaha.fileparsingsystem.file.entity.Like;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface LikesMapper {
    public void insert(Like like);
}

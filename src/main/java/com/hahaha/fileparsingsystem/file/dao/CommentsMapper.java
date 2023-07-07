package com.hahaha.fileparsingsystem.file.dao;

import com.hahaha.fileparsingsystem.file.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentsMapper {
    public void insert(Comment comment);
}

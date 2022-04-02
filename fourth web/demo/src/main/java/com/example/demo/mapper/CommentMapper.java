package com.example.demo.mapper;

import com.example.demo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer id);

    List<Comment> selectAll();
    List<Comment> selectByRid(int rid);

    int updateByPrimaryKey(Comment record);
}
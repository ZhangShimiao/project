package com.example.demo.mapper;

import com.example.demo.entity.Like;
import java.util.List;

public interface LikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Like record);

    Like selectByPrimaryKey(Integer id);

    List<Like> selectAll();

    int updateByPrimaryKey(Like record);
}
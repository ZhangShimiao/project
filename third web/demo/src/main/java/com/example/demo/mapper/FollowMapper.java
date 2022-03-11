package com.example.demo.mapper;

import com.example.demo.entity.Follow;
import com.example.demo.entity.User;

import java.util.List;

public interface FollowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Follow record);

    Follow selectByPrimaryKey(Integer id);

    List<Follow> selectAll();

    int updateByPrimaryKey(Follow record);

    Follow selectExist(Follow record);
//
    List<User> getFollowByUser(int uid);
    List<User> getFansByUser(int uid);
}
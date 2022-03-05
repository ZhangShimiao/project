package com.example.demo.service;

import com.example.demo.entity.Follow;
import com.example.demo.entity.User;
import com.example.demo.mapper.FollowMapper;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FollowService {
    @Resource
    private FollowMapper mapper;

    public int add(Follow bean) {
        return mapper.insert(bean);
    }
    public int delete(int id) {
        return mapper.deleteByPrimaryKey(id);
    }
    public List<User> getFollowByUser(int uid) {
        return mapper.getFollowByUser(uid);
    }
    public List<User> getFansByUser(int uid) {
        return mapper.getFansByUser(uid);
    }
    public Follow selectExist(Follow bean) {
        return mapper.selectExist(bean);
    }
}

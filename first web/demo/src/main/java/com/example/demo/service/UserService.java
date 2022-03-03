package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.User;

import javax.annotation.Resource;

@Service
public class UserService {
//    @Autowired
//    private UserMapper mapper;
//    public User selectByName(String account) {
//        return mapper.selectByName(account);
//    }
    @Resource
    UserMapper userMapper;
//
//    //查找用户名
//    public User findUserName(String userName) {
//        return userMapper.findUserName(userName);
//    }
//
//    //查找账号密码
//    public String findPassword(String password) {
//        return userMapper.findPassword(password);
//    }
    public User loginPage(String account, String password) {
        return userMapper.loginPage(account, password);
    }
    public User userType(String account, String password) {
        return userMapper.userType(account, password);
    }
//    添加管理员
    public int addUser(User record){ return userMapper.addUser(record); }
//    public User selectByName(String account) {
//        return userMapper.selectByName(account);
//    }

}

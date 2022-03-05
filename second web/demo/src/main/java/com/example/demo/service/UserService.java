package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.User;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> selectAllByTypeCount(int type, int page, int limit) {
        int start = (page - 1) * limit;
        Map<String, Object> map = new HashMap<String, Object>();
        int size = userMapper.selectAllByTypeCount(type);
        List<User> data = userMapper.selectAllByType(type, start, size);
        map.put("data", data);
        map.put("count", size);
        return map;
    }
    public User loginPage(String account, String password) {
        return userMapper.loginPage(account, password);
    }
    public User userType(String account, String password) {
        return userMapper.userType(account, password);
    }
//    添加管理员
    public int addUser(User record){ return userMapper.insert(record); }
    public User selectByPrimaryKey(int id) {
        return userMapper.selectByPrimaryKey(id);
    }
    public User getUserMsg(int id,int myUid) {
        return userMapper.selectUserMsg(id,myUid);
    }
    public int updateUser(User record) {
        return userMapper.updateByPrimaryKey(record);
    }
    public int deleteUser(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }
    public User selectByName(String account) {
        return userMapper.selectByName(account);
    }
    public int editUserPassword(User record) {
        return userMapper.editUserPassword(record);
    }
//    public User selectByName(String account) {
//        return userMapper.selectByName(account);
//    }

}

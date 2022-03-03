package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(User record);
//
//    User selectByPrimaryKey(Integer id);
     User selectByName(String account);
//
//    List<User> selectAll();
//
//    int updateByPrimaryKey(User record);
      List<User> selectAll();
      User loginPage(@Param("account") String account, @Param("password") String password);
      User userType(String account, String password);

      int addUser(User recoord);
//      User findUserName(String userName);
//
//      String findPassword(String password);

}
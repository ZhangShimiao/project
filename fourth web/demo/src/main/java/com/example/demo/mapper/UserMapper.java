package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
     int deleteByPrimaryKey(Integer id);
//
     int insert(User record);
//
     User selectByPrimaryKey(Integer id);
     User selectByName(String account);
//
//    List<User> selectAll();
//
      int updateByPrimaryKey(User record);
      List<User> selectAll();
      User loginPage(@Param("account") String account, @Param("password") String password);
      User userType(String account, String password);
      int editUserPassword(User record);
//      int addUser(User recoord);
      int selectAllByTypeCount(int type);
      User selectUserMsg(@Param("id")int id,@Param("myUid")int myUid);
      List<User> selectAllByType(@Param("type") int type,@Param("start") int start, @Param("size") int size);
//      User findUserName(String userName);
//
//      String findPassword(String password);

}
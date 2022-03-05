package com.example.demo.mapper;

import com.example.demo.entity.Recipes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecipesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recipes record);

    Recipes selectByPrimaryKey(Integer id);
    Recipes selectByPrimaryKey2(@Param("id")int id,@Param("uid")int uid);

    List<Recipes> selectAll();
    List<Recipes> selectByType(int type);
    int selectAllCount();
    List<Recipes> selectByPage(@Param("start") int start, @Param("size") int size);
    List<Recipes> selectByUser(int uid);
    int updateByPrimaryKey(Recipes record);
}
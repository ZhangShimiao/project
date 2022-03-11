package com.example.demo.mapper;

import com.example.demo.entity.Recipes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface RecipesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recipes record);

    Recipes selectByPrimaryKey(Integer id);
    Recipes selectByPrimaryKey2(@Param("id")int id,@Param("uid")int uid);

    List<Recipes> selectAll();
    List<Recipes> selectByType(int type);
    List<Recipes> recipesSearch(@Param("keyword") String keyword);
    int selectAllCount();
    List<Recipes> selectByPage(@Param("start") int start, @Param("size") int size);
    List<Recipes> selectByUser(int uid);
    int updateByPrimaryKey(Recipes record);
}
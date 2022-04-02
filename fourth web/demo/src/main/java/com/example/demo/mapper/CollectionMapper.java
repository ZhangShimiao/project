package com.example.demo.mapper;

import com.example.demo.entity.Collection;
import com.example.demo.entity.Recipes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CollectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collection record);

    Collection selectByPrimaryKey(Integer id);

    List<Collection> selectAll();
    Collection selectExist(@Param("uid") int uid, @Param("rid") int rid);
    List<Recipes> selectByUser(@Param("uid") int uid);
    int updateByPrimaryKey(Collection record);
}
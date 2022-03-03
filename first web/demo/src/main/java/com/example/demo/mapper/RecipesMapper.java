package com.example.demo.mapper;

import com.example.demo.entity.Recipes;
import java.util.List;

public interface RecipesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recipes record);

    Recipes selectByPrimaryKey(Integer id);

    List<Recipes> selectAll();

    int updateByPrimaryKey(Recipes record);
}
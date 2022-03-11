package com.example.demo.mapper;

import com.example.demo.entity.Recipes;
import com.example.demo.entity.Scan;
import java.util.List;

public interface ScanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Scan record);

    Scan selectByPrimaryKey(Integer id);

    List<Scan> selectAll();
    List<Recipes> getByUid(int uid);
    Scan selectExist(Scan record);
    int updateByPrimaryKey(Scan record);
}
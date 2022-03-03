package com.example.demo.mapper;

import com.example.demo.entity.Scan;
import java.util.List;

public interface ScanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Scan record);

    Scan selectByPrimaryKey(Integer id);

    List<Scan> selectAll();

    int updateByPrimaryKey(Scan record);
}
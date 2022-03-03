package com.example.demo.mapper;

import com.example.demo.entity.Collection;
import java.util.List;

public interface CollectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collection record);

    Collection selectByPrimaryKey(Integer id);

    List<Collection> selectAll();

    int updateByPrimaryKey(Collection record);
}
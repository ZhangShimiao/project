package com.example.demo.service;

import com.example.demo.entity.Collection;
import com.example.demo.entity.Recipes;
import com.example.demo.mapper.CollectionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollectionService {
    @Resource
    private CollectionMapper mapper;
    public Collection selectExist(int uid, int rid) {
        return mapper.selectExist(uid,rid);
    }
    public int add(Collection bean) {
        return mapper.insert(bean);
    }
    public int delete(int id) {
        return mapper.deleteByPrimaryKey(id);
    }
    public List<Recipes> selectByUser(int uid) {
        return  mapper.selectByUser(uid);
    }

    public List<Collection> selectAll() {
        return  mapper.selectAll();
    }
}

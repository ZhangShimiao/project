package com.example.demo.service;

import com.example.demo.entity.Type;
import com.example.demo.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeService {
    @Resource
    private TypeMapper mapper;
    public Map<String, Object> selectAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Type> data = mapper.selectAll();
        map.put("data", data);
        return map;
    }
    public Type selectByPrimaryKey(int id) {
        return mapper.selectByPrimaryKey(id);
    }
    public int addType(Type type) {
        return mapper.insert(type);
    }
    public int deleteType(int id) {
        return mapper.deleteByPrimaryKey(id);
    }
    public int updateRecipeType(Type type) {
        return mapper.updateByPrimaryKey(type);
    }
}

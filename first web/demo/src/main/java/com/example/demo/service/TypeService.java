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
}

package com.example.demo.service;

import com.example.demo.entity.Recipes;
import com.example.demo.entity.Scan;
import com.example.demo.mapper.ScanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScanService {
    @Resource
    private ScanMapper mapper;


    public int add(Scan bean) {
        return mapper.insert(bean);
    }
    public List<Recipes> getByUid(int uid) {
        return mapper.getByUid(uid);
    }
    public Scan selectExist(Scan bean) {
        return mapper.selectExist(bean);
    }
}

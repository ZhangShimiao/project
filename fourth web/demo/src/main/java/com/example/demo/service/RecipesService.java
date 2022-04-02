package com.example.demo.service;

import com.example.demo.entity.Recipes;
import com.example.demo.mapper.RecipesMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipesService {
    @Resource
    private RecipesMapper recipesMapper;

    public Map<String, Object> selectByPage(int page, int limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        int start = (page - 1) * limit;
        int size = recipesMapper.selectAllCount();
        List<Recipes> data = recipesMapper.selectByPage(start, limit);
        map.put("data", data);
        map.put("count", size);
        return map;
    }
    public List<Recipes> selectByUser(int uid) {
        return  recipesMapper.selectByUser(uid);
    }
    public List<Recipes> selectByType(int type) {
        return  recipesMapper.selectByType(type);
    }
    public List<Recipes> selectAll() {
        return  recipesMapper.selectAll();
    }
    public Map<String, Object> recipesSearch(String keyword) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Recipes> data = recipesMapper.recipesSearch(keyword);
        map.put("data", data);
        map.put("count", data.size());
        return map;
    }
    public int deleteRecipes(int id) {
        return recipesMapper.deleteByPrimaryKey(id);
    }
    public int addRecipes(Recipes record) {
        return recipesMapper.insert(record);
    }
    public Recipes selectById(int id) {
        return recipesMapper.selectByPrimaryKey(id);
    }
    public Recipes selectById2(int id,int uid) {
        return recipesMapper.selectByPrimaryKey2(id, uid);
    }
    public int editRecipes(Recipes record) {
        return recipesMapper.updateByPrimaryKey(record);
    }
}

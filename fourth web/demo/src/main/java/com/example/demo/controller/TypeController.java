package com.example.demo.controller;

import com.example.demo.entity.Type;
import com.example.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;
//   展示食谱列表
    @RequestMapping("/typeList")
    @ResponseBody
    public Map<String, Object> list() {
//        ModelAndView modelAndView= new ModelAndView();
//        Map<String, Object> map = typeService.selectAll();
//        modelAndView.addObject("map",map);
//        modelAndView.setViewName("index");
//        return modelAndView;
        try {
            Map<String, Object> map = typeService.selectAll();
            map.put("code", 0);
            map.put("msg", "success");
            System.out.println(map);
            return map;
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", 1);
            map.put("msg", "请稍后再试");
            map.put("data", "[]");
            map.put("count", 0);
            e.printStackTrace();
            return map;
        }

    }

    @RequestMapping(value = "/deleteRecipeType")
    @ResponseBody
    public Map<String, Object> delete(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int i = typeService.deleteType(id);
            if (i != 0) {
                map.put("code", 0);
                map.put("msg", "success");
            } else {
                map.put("code", 1);
                map.put("msg", "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Map<String, Object> selectById(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Type type = typeService.selectByPrimaryKey(id);
            map.put("code", 0);
            map.put("msg", "操作失败");
            map.put("data", type);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
            map.put("data", "");
        }
        return map;
    }

    @RequestMapping(value = "/editRecipeType")
    @ResponseBody
    public Map<String, Object> update(Type type) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int i = typeService.updateRecipeType(type);
            if (i != 0) {
                map.put("code", 0);
                map.put("msg", "success");
            } else {
                map.put("code", 1);
                map.put("msg", "操作失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/addRecipeType")
    @ResponseBody
    public Map<String, Object> addType(Type type) {
        System.out.println(type);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int i = typeService.addType(type);
            if (i != 0) {
                map.put("code", 0);
                map.put("msg", "success");
            } else {
                map.put("code", 1);
                map.put("msg", "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }
}

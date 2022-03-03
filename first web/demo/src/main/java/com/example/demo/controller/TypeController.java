package com.example.demo.controller;

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
    @RequestMapping("/list")
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
            map.put("msg", "服务器繁忙");
            map.put("data", "[]");
            map.put("count", 0);
            e.printStackTrace();
            return map;
        }

    }
}

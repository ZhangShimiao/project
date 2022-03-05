package com.example.demo.controller;

import com.example.demo.entity.Follow;
import com.example.demo.service.FollowService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/follow") // 表示命名空间
public class FollowController {
    @Resource
    private FollowService service;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, Object> add(Follow bean) {
        System.out.println(bean);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Follow follow = service.selectExist(bean);
            int flag;
            if (follow == null) {
                flag = service.add(bean);
            } else {
                flag = service.delete(follow.getId());
            }
            System.out.println(bean);
            if (flag != 0) {
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

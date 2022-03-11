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
@RequestMapping("/follow") // Represents a namespace
public class FollowController {
    @Resource
    private FollowService service;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, Object> add(Follow bean) {
        //Add a follow.
        System.out.println(bean);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //Determine whether the follow entity is exist or not.
            Follow follow = service.selectExist(bean);
            int flag;
            //If this follow is null, add it, otherwise, delete it.
            if (follow == null) {
                flag = service.add(bean);
            } else {
                flag = service.delete(follow.getId());
            }
            System.out.println(bean);
            //If the flag is not 0, the operation is successful.
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

    @RequestMapping(value = "/getFollowByUser")
    @ResponseBody
    public Map<String, Object> getFollowByUser(int uid) {
        //Get one user's follow list.
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //Set the code, message and data in the map.
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", service.getFollowByUser(uid));
            return map;
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "服务器繁忙");
            map.put("data", "[]");
            map.put("count", 0);
            e.printStackTrace();
            return map;
        }

    }

    @RequestMapping(value = "/getFansByUser")
    @ResponseBody
    public Map<String, Object> getFansByUser(int uid) {
        //Get one user's fans list.
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", service.getFansByUser(uid));
            return map;
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "服务器繁忙");
            map.put("data", "[]");
            map.put("count", 0);
            e.printStackTrace();
            return map;
        }

    }
}

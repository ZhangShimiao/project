package com.example.demo.controller;

import com.example.demo.entity.Collection;
import com.example.demo.service.CollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/collection") // Represents a namespace
public class CollectionController {
    @Resource
    private CollectionService service;

    @RequestMapping(value = "/collect")
    @ResponseBody
    public Map<String, Object> collection(Collection bean) {
        System.out.println(bean);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //Determine whether the collection is exist or not.
            Collection collection=service.selectExist(bean.getUid(), bean.getRid());
            int flag;
            //If the collection is not null, delete this collection.
            if(collection!=null) {
                flag = service.delete(collection.getId());
            }else {
                //If the collection is null, add this collection
                flag = service.add(bean);
            }
            System.out.println(bean);
            //If the flag is 0,the operation is fail.
            if (flag == 0) {
                map.put("code", 1);
                map.put("msg", "操作失败");
            } else {
                //Set the code to 0, and message to success in map.
                map.put("code", 0);
                map.put("msg", "success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }
    @RequestMapping(value = "/listByUid")
    @ResponseBody
    public Map<String, Object> listByUid(int uid) {
        //List a user's collection through the user id.
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //Set the code, message and data in the map.
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", service.selectByUser(uid));
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

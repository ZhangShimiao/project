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
@RequestMapping("/collection") // 表示命名空间
public class CollectionController {
    @Resource
    private CollectionService service;

    @RequestMapping(value = "/collect")
    @ResponseBody
    public Map<String, Object> collection(Collection bean) {
        System.out.println(bean);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Collection collection=service.selectExist(bean.getUid(), bean.getRid());
            int flag;
            if(collection!=null) {
                flag = service.delete(collection.getId());
            }else {
                flag = service.add(bean);
            }
            System.out.println(bean);
            if (flag == 0) {
                map.put("code", 1);
                map.put("msg", "操作失败");
            } else {
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
        Map<String, Object> map = new HashMap<String, Object>();
        try {
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

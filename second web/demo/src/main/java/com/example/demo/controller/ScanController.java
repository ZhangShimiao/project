package com.example.demo.controller;

import com.example.demo.entity.Scan;
import com.example.demo.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/scan") // 表示命名空间
public class ScanController {
    @Resource
    private ScanService service;

    @RequestMapping(value = "/listByUid")
    @ResponseBody
    public Map<String, Object> listByUid(int uid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", service.getByUid(uid));
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

    @RequestMapping(value = "/addScan")
    @ResponseBody
    public Map<String, Object> add(Scan bean) {
        System.out.println(bean);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Scan scan = service.selectExist(bean);
            if (scan != null) {
                map.put("code", 0);
                map.put("msg", "success");
                return map;
            }
            int flag = service.add(bean);
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

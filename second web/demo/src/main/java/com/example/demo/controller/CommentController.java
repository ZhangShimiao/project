package com.example.demo.controller;


import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/comment") // 表示命名空间
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/commentListByRid")
    @ResponseBody
    public Map<String, Object> commentListByRid(int rid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", commentService.selectByRid(rid));
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

    @RequestMapping(value = "/deleteComment")
    @ResponseBody
    public Map<String, Object> deleteComment(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int i = commentService.deleteComment(id);
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
    @RequestMapping(value = "/addComment")
    @ResponseBody
    public Map<String, Object> add(Comment bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int flag = commentService.add(bean);
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

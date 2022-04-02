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
@RequestMapping("/comment") // Represents a namespace
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/commentListByRid")
    @ResponseBody
    public Map<String, Object> commentListByRid(int rid) {
        //List one recipe's comment.
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
        //Delete a comment
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int i = commentService.deleteComment(id);
            //If i is not 0, delete success.
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
        //Add a comment.
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int flag = commentService.add(bean);
            //If the flag is not 0, the add operation is successful.
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

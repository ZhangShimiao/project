package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    public List<Comment> selectByRid(int rid) {
        return  commentMapper.selectByRid(rid);
    }
    public int add(Comment bean) {
        return commentMapper.insert(bean);
    }
    public int deleteComment(int id) {
        return commentMapper.deleteByPrimaryKey(id);
    }
}

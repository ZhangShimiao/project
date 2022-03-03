package com.example.demo.entity;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer rid;

    private Integer commentuserid;

    private Integer becommentuserid;

    private String content;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getCommentuserid() {
        return commentuserid;
    }

    public void setCommentuserid(Integer commentuserid) {
        this.commentuserid = commentuserid;
    }

    public Integer getBecommentuserid() {
        return becommentuserid;
    }

    public void setBecommentuserid(Integer becommentuserid) {
        this.becommentuserid = becommentuserid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
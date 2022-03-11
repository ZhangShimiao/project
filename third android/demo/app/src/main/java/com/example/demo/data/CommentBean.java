package com.example.demo.data;

import java.util.Date;

public class CommentBean {
    //Comment entity
    //The id of comment.
    private Integer id;
    //The id of the recipe.
    private Integer rid;
    //The id of comment user id.
    private Integer commentuserid;
    //The id of be commented user id.
    private Integer becommentuserid;
    //The content of comment.
    private String content;
    //The create time of comment.
    private Date createtime;
    //The username and head image of comment user and be commented user.
    private String commentUserName,commentUserHeadimg,beCommentUserName,beCommentUserHeadimg;


    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentUserHeadimg() {
        return commentUserHeadimg;
    }

    public void setCommentUserHeadimg(String commentUserHeadimg) {
        this.commentUserHeadimg = commentUserHeadimg;
    }

    public String getBeCommentUserName() {
        return beCommentUserName;
    }

    public void setBeCommentUserName(String beCommentUserName) {
        this.beCommentUserName = beCommentUserName;
    }

    public String getBeCommentUserHeadimg() {
        return beCommentUserHeadimg;
    }

    public void setBeCommentUserHeadimg(String beCommentUserHeadimg) {
        this.beCommentUserHeadimg = beCommentUserHeadimg;
    }

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

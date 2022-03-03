package com.example.demo.entity;

public class Follow {
    private Integer id;

    private Integer followid;

    private Integer befollowid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFollowid() {
        return followid;
    }

    public void setFollowid(Integer followid) {
        this.followid = followid;
    }

    public Integer getBefollowid() {
        return befollowid;
    }

    public void setBefollowid(Integer befollowid) {
        this.befollowid = befollowid;
    }
}
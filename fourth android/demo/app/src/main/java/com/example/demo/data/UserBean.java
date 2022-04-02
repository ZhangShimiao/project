package com.example.demo.data;

import java.io.Serializable;

public class UserBean implements Serializable {
    //User entity
    //The id of user.
    private Integer id;
    //The nickname of user.
    private String nickname;
    //The account of user.
    private String account;
    //The password, type of user.
    private String password;
    private Integer type;
    //The security question, security answer and head image of user.
    private String question;
    private String answer;
    private String headimg;

    private int followNum,fansNum,scanNum,isFollow;

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public int getFollowNum() {
        return followNum;
    }
    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }
    public int getFansNum() {
        return fansNum;
    }
    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }
    public int getScanNum() {
        return scanNum;
    }
    public void setScanNum(int scanNum) {
        this.scanNum = scanNum;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getHeadimg() {
        return headimg;
    }
    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", nickname=" + nickname + ", account=" + account + ", password=" + password
                + ", type=" + type + ", question=" + question + ", answer=" + answer + ", headimg=" + headimg + "]";
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
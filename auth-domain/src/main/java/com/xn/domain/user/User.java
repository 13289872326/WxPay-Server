package com.xn.domain.user;

import java.io.Serializable;

/**
 * 用户类
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户ID
    public String id;
    //用户密码
    public String pwd;
    //用户名
    public String userName;
    //昵称
    public String nickName;
    //手机号码
    public String phoneNum;
    //头像URL
    public String imgUrl;
    //上级ID
    public String highLevel;
    //WX小程序给出的唯一标示
    public String openId;
    //用户角色 1.游客  2.VIP  3.代理
    public int roleType;
    //剩余视频解析次数
    public int remainNum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHighLevel() {
        return highLevel;
    }

    public void setHighLevel(String highLevel) {
        this.highLevel = highLevel;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public int getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(int remainNum) {
        this.remainNum = remainNum;
    }

    public User() {
    }

    public User(String id, String pwd, String userName, String nickName, String phoneNum, String imgUrl, String highLevel, String openId, int roleType, int remainNum) {
        this.id = id;
        this.pwd = pwd;
        this.userName = userName;
        this.nickName = nickName;
        this.phoneNum = phoneNum;
        this.imgUrl = imgUrl;
        this.highLevel = highLevel;
        this.openId = openId;
        this.roleType = roleType;
        this.remainNum = remainNum;
    }
}
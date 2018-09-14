package com.example.newtest.bean;

/**
 * Created by Mark on 2018/4/7.
 */

public class UserData extends BaseBean {
    private String userImg;
    private String realName;

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}

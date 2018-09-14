package com.example.newtest.bean;

import java.io.Serializable;

/**
 * Created by Mark on 2018/7/13.
 */

public class CommonBannerBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;

    private String pic_url;

    private String website;

    private String islogin;


    public String getIslogin() {
        return islogin;
    }

    public void setIslogin(String islogin) {
        this.islogin = islogin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}

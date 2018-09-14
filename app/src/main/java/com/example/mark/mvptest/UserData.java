package com.example.mark.mvptest;

import java.io.Serializable;

/**
 * Created by Mark on 2018/4/5.
 */

public class UserData implements Serializable {
    private String niceName;
    private String img;
    private String memo;

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

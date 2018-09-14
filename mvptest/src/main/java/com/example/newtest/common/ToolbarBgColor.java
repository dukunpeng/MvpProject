package com.example.newtest.common;

import com.example.newtest.R;

/**
 * Created by Mark on 2018/4/8.
 */

public enum ToolbarBgColor {
    RED_BG_COLOR(0, R.color.toolbar_red_bg_color),
    WHITE_BG_COLOR(1, R.color.toolbar_red_bg_color),;
    private int id;
    private int colorResource;

    ToolbarBgColor(int id, int colorResource) {
        this.id = id;
        this.colorResource = colorResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }

}

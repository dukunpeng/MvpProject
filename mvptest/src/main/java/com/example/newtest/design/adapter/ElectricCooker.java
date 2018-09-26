package com.example.newtest.design.adapter;

/**
 * 110v的电饭煲
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class ElectricCooker {

    private USA110V usa110;//110电饭煲要用110电压接口

    public ElectricCooker(USA110V usa110) {
        super();
        this.usa110 = usa110;
    }
    public void cook(){
        usa110.connect();//通电
        System.out.println("开始做饭。。。");
    }
}

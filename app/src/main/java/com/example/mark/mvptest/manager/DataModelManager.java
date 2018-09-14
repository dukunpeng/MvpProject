package com.example.mark.mvptest.manager;

import com.example.mark.mvptest.base.BaseModel;

/**
 * Created by Mark on 2018/4/4.
 */

public class DataModelManager {
    private static BaseModel model;
    public static BaseModel newInstance(String className){
        //利用反射机制获得对应Model对象的引用
        try {
            return  model = (BaseModel)Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

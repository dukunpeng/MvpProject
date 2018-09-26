package com.example.newtest.design.factory;

/**
 * 反射实现工厂类
 * @author Mark
 * @create 2018/9/26
 * @Describe
 */

public class Factory {

    public static <T extends Product> T create(Class<T> clz){
        Product product =null;
        try {
            product = (Product) Class.forName(clz.getName()).newInstance();
        }catch (Exception e){
            return null;
        }
        return (T) product;
    }
}

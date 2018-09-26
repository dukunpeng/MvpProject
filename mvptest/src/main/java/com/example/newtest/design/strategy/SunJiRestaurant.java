package com.example.newtest.design.strategy;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class SunJiRestaurant implements IStrategy {
    @Override
    public void breakfast() {
        System.out.println("包子+稀饭");
    }
}

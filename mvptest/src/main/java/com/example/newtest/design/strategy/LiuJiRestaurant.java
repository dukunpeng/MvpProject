package com.example.newtest.design.strategy;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class LiuJiRestaurant implements IStrategy {
    @Override
    public void breakfast() {

        System.out.println("鸡蛋+面包");
    }
}

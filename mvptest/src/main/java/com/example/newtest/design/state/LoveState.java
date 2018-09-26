package com.example.newtest.design.state;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class LoveState implements PersonState {
    @Override
    public void movies() {
        System.out.println("看爱情片恐怖片");
    }

    @Override
    public void shopping() {
        System.out.println("买衣服买包包");
    }
}

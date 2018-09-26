package com.example.newtest.design.state;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class DogState implements PersonState {
    @Override
    public void movies() {
        System.out.println("躲被窝看岛国大片");
    }

    @Override
    public void shopping() {
        System.out.println("不逛街");
    }
}

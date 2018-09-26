package com.example.newtest.design.decorator;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class PersonComponent implements Component {
    @Override
    public void decorate() {
        System.out.println("我穿了一件T恤，一条牛仔，一双奥康皮鞋");
    }
}

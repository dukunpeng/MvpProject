package com.example.newtest.design.command;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class ChildEat implements EatReceiver {
    @Override
    public void eat() {

        System.out.println("接收到吃饭的命令，我吃一小碗面");
    }
}

package com.example.newtest.design.intermediary;

/**
 * @author Mark
 * @create 2018/9/26
 * @Describe
 */
public class Purchaser extends Person {//买房者类，继承Person
    public Purchaser(HouseMediator houseMediator) {
        super(houseMediator);
    }

    @Override
    public void send(String message) {
        System.out.println("买房者发布信息：" + message);
        houseMediator.notice(this, message);
    }

    @Override
    public void getNotice(String message) {
        System.out.println("买房者收到消息：" + message);
    }
}

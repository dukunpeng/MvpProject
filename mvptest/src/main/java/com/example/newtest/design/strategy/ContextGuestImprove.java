package com.example.newtest.design.strategy;

/**
 * 策略模式升级版，将实际提供策略的对象通过set方式设置进来，以便重复利用Context对象，昨晚这个策略还可以再做其它的策略
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class ContextGuestImprove {

    private IStrategy iStrategy;
    public void setStrategy(IStrategy iStrategy){
        this.iStrategy = iStrategy;
    }
    public void haveBreakfast(){
        iStrategy.breakfast();
    }

    public void play(){
        //这里可以再来策略
    }
}

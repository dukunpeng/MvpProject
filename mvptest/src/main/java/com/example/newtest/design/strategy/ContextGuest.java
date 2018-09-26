package com.example.newtest.design.strategy;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class ContextGuest   {

    private IStrategy iStrategy;
    public ContextGuest(IStrategy iStrategy){
        this.iStrategy = iStrategy;
    }
    public void haveBreakfast(){
        iStrategy.breakfast();
    }
}

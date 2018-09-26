package com.example.newtest.design.chain;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class PositionHandler extends Abstracthandler {

    private String position;

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public void operate() {
        System.out.println(position+"自己要处理的事情");
        if (getHandler()!=null){
            //如果下一个责任链还有要处理的，则再放给下一任处理
            getHandler().operate();
        }
    }
}

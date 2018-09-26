package com.example.newtest.design.facade;

/**
 * @author Mark
 * @create 2018/9/26
 * @Describe
 */

public class GameSdk implements IGame{


    @Override
    public void login() {

        LoginManager loginManager = new LoginManager();
        loginManager.login();
    }

    @Override
    public void pay(int money) {

        PayManager payManager = new PayManager();
        payManager.pay(money);
    }
}

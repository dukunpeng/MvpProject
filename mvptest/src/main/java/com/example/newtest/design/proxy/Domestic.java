package com.example.newtest.design.proxy;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class Domestic implements IPeople {
    @Override
    public void buy() {
        System.out.println("国内要买一个包");
    }
}

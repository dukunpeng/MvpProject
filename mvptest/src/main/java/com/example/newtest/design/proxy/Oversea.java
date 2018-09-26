package com.example.newtest.design.proxy;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class Oversea implements IPeople {
    private IPeople people;
   public Oversea(IPeople people){

       this.people = people;

    }
    @Override
    public void buy() {//实现代理
        System.out.println("国外要买一个包");
        people.buy();
    }
}

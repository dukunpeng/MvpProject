package com.example.newtest.design.decorator;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class HatDecorator extends AbstractDecorator {
    public HatDecorator(Component component) {
        super(component);
    }

    @Override
    public void decorate() {
        super.decorate();
        System.out.println("加一顶Nike帽子");
    }
}

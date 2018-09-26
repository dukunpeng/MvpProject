package com.example.newtest.design.decorator;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class SweaterDecorator extends AbstractDecorator {
    public SweaterDecorator(Component component) {
        super(component);
    }

    @Override
    public void decorate() {
        super.decorate();
        System.out.println("添加一件毛衣");
    }
}

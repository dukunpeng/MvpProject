package com.example.newtest.design.decorator;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public abstract class AbstractDecorator implements Component {
    private Component component;
    public AbstractDecorator(Component component){
        this.component = component;
    }

    @Override
    public void decorate() {
        if (component!=null){}
        component.decorate();
    }
}

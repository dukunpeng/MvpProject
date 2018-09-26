package com.example.newtest.design.factory;

/**
 * @author Mark
 * @create 2018/9/26
 * @Describe
 */

public class ProductAFactory extends AbstractFactory {
    @Override
    Product create() {
        return new ProductA();
    }
}

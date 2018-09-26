package com.example.newtest.design.factory;

/**
 * @author Mark
 * @create 2018/9/26
 * @Describe
 */

public class ProductBFactory extends AbstractFactory {
    @Override
    Product create() {
        return new ProductB();
    }
}
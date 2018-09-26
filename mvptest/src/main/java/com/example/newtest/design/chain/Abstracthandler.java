package com.example.newtest.design.chain;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public abstract class Abstracthandler implements IHandler {
    private IHandler handler;

    public IHandler getHandler() {
        return handler;
    }

    public void setHandler(IHandler handler) {
        this.handler = handler;
    }
}

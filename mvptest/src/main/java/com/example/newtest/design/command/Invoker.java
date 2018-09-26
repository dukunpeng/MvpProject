package com.example.newtest.design.command;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class Invoker {
    private Command command;

    public Invoker setCommand(Command command) {
        this.command = command;
        return this;
    }
    public void invoke(){
        command.execute();
    }
}

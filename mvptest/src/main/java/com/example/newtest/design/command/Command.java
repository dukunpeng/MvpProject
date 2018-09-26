package com.example.newtest.design.command;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class Command {
    private EatReceiver receiver ;
    public Command(EatReceiver receiver){
        this.receiver = receiver;
    }
    public void execute(){
        receiver.eat();
    }
}

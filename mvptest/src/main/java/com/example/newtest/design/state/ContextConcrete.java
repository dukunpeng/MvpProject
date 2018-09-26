package com.example.newtest.design.state;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class ContextConcrete implements PersonState{
    private PersonState personState;

    public void setPersonState(PersonState personState){
        this.personState = personState;
    }

    /**
     * 坠入爱河
     */
    public void fallInLove(){
        System.out.println("恋爱了,陷入热恋状态:");
        setPersonState(new LoveState());
    }

    /**
     * 失恋
     */
    public void disappointmentInLove(){
        System.out.println("失恋了，变单身狗:");
        setPersonState(new DogState());
    }

    @Override
    public void movies() {
        personState.movies();
    }

    @Override
    public void shopping() {

        personState.shopping();
    }
}

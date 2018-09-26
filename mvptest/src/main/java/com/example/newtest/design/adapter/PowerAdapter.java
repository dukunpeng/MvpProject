package com.example.newtest.design.adapter;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */
//电压适配器
//为了能够适配110V电饭煲的电源接口，我们要继承110接口
//适配器角色，实现目标角色接口
public class PowerAdapter implements USA110V {

    /* 继承110接口，可以使用美国电饭煲，有持有220引用可以用220工作 */
    private CN220V cn220;// 用220v=接口进行适配

    public PowerAdapter(CN220V cn220){
        super();
        this.cn220 = cn220;
    }

    @Override
    public void connect() {

        cn220.connect();
    }
}

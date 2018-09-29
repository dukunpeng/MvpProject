package com.example.newtest.test.mmkv;

import com.example.newtest.kit.Kits;
import com.tencent.mmkv.MMKV;

/**
 * @author Mark
 * @create 2018/9/28
 * @Describe
 */

public class MmkvUse {
    public void useMmkv(){

        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode("en_name","mark");
        mmkv.encode("en_age",27);
        mmkv.putString("name","mark");
        mmkv.putInt("age",27);

        System.out.println("en_name:"+mmkv.decodeString("en_name"));
        System.out.println("en_age:"+mmkv.decodeInt("en_age"));
        System.out.println("name:"+mmkv.getString("name","sssssssssss"));
    }
    public void useSharedPrefrence(){

    }
}

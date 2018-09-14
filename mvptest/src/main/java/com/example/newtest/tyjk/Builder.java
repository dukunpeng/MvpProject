package com.example.newtest.tyjk;

/**
 * Created by Mark on 2018/4/12.
 */

public interface Builder{

       int TYPE_NORMAL = 1;
       int TYPE_ONLY_COMPRESS = 2;
       int TYPE_ONLY_ENCRYPT = 3;
       int TYPE_COMPRESS_ENCRYPT = 4;
        void encodeData();
       void decodeData();
      void compress();
       void uncompress();

    public BaseBean builder();
}

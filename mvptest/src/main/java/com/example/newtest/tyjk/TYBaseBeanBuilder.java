package com.example.newtest.tyjk;

/**
 * Created by Mark on 2018/4/12.
 */

public class TYBaseBeanBuilder implements Builder {
    private TYBaseBean bean = new TYBaseBean();

    public TYBaseBeanBuilder initDefaultBean(TYBaseBean bean) {
        this.bean = bean;
        return this;
    }

    @Override
    public void encodeData() {

        if (!bean.isEncrypt()){
            //TODO 加密
            bean.setEncrypt(true);
        }

    }

    @Override
    public void decodeData() {
        if (bean.isEncrypt()){
            //TODO 解密
            bean.setEncrypt(false);
        }
    }

    @Override
    public void compress() {
        if (!bean.isCompress()){
            //TODO 加压
            bean.setCompress(true);
        }
    }

    @Override
    public void uncompress() {
        if (bean.isCompress()){
            //TODO 解压
            bean.setCompress(false);
        }
    }

    public TYBaseBean builder() {

        decodeData();
        uncompress();
        return bean;
    }
}

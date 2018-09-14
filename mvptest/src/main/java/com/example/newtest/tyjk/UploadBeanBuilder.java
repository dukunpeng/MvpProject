package com.example.newtest.tyjk;

import android.text.TextUtils;

import com.example.newtest.kit.Codec;
import com.example.newtest.kit.Kits;

import java.util.Date;

/**
 * Created by Mark on 2018/4/12.
 */

public class UploadBeanBuilder implements Builder{

    private BaseUpLoadBean bean = new BaseUpLoadBean();


    public UploadBeanBuilder initDefaultBean(String param,String code) {
        bean.setClienTime(Kits.Date.getYmdhms(new Date().getTime()));
        String deviceId = "XXXXXXXXXXXXXX";
        bean.setDeviceId(deviceId);
        bean.setCode(code);
        bean.setParamStr(param);
        bean.setPlatform("2");
        bean.setToken("");
        bean.setVersion("4.2.0_"+"170"

                /*Kits.Package.getVersionName()
                SystemUtils.getAppVersionName(mContext) + "_"
                + SystemUtils.getAppVersionCode(mContext)*/);
       // bean.setSingnStr("");
//        if (!TextUtils.isEmpty(clientDataMd5)) {
//            bean.setClientDataMD5(clientDataMd5);
//        }
        return this;
    }

    public UploadBeanBuilder fromType(final int type){
        switch (type){
            case TYPE_NORMAL:
                bean.setEncrypt(false);
                bean.setCompress(false);
                break;

            case TYPE_ONLY_COMPRESS:
                bean.setEncrypt(false);
                bean.setCompress(true);
                break;

            case TYPE_ONLY_ENCRYPT:
                bean.setEncrypt(true);
                bean.setCompress(false);
                break;

            case TYPE_COMPRESS_ENCRYPT:
                bean.setEncrypt(true);
                bean.setCompress(true);
                break;
        }
        if (bean.isEncrypt()){
            encodeData();
        }
        if (bean.isCompress()){
            compress();
        }
        if (!TextUtils.isEmpty(bean.getParamStr())) {
            bean.setSingnStr(Codec.MD5.getMessageDigest((bean.getParamStr() + "HPD").getBytes()));
        }
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

    public BaseUpLoadBean builder() {
        return bean;
    }
}

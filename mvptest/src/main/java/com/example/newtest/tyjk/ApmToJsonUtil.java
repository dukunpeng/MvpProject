package com.example.newtest.tyjk;

import com.example.newtest.common.APPClientParam;
import com.example.newtest.common.HttpConfig;
import com.example.newtest.kit.Kits;

/**
 * Created by Mark on 2018/7/11.
 */

public class ApmToJsonUtil {
    public static String apmToJsonParam(final APPClientParam apm,final String method){
        return Kits.Gsons.instance().toJson(Director.createBuilder(new UploadBeanBuilder()
                .initDefaultBean(Kits.Gsons.instance().toJson(apm),method)
                .fromType(Builder.TYPE_NORMAL))
                .builder());
    }
    public static String apmToJsonParam(final APPClientParam apm,final HttpConfig method){
        return Kits.Gsons.instance().toJson(Director.createBuilder(new UploadBeanBuilder()
                .initDefaultBean(Kits.Gsons.instance().toJson(apm),method.getMethodName())
                .fromType(Builder.TYPE_NORMAL))
                .builder());
    }
}

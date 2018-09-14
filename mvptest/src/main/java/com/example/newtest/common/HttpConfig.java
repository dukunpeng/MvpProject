package com.example.newtest.common;

/**
 * Created by Mark on 2018/7/11.
 */

public enum HttpConfig {

    METHOD_GET_HOME_BANNER_LIST("获取首页banner","GetHomeBannerList"),
    METHOD_UNIFY_USER_LOGIN("用户登录","Unify_User_Login") ,
    METHOD_GET_INVEST_PRODUCT_INFO("获取标的详情","GetInvestProductInfo"),
    METHOD_ANDROID_SYS_UPDATE_M("获取系统更新信息","AndroidSysUpdateM")
            ;
    private String memo;
    private String methodName;

    HttpConfig(String memo, String methodName) {
        this.memo = memo;
        this.methodName = methodName;
    }

    public String getMemo() {
        return memo;
    }

    public String getMethodName() {
        return methodName;
    }

    public final static String getMemo(final String methodName) {
        for (HttpConfig c : values()) {
            if (c.getMethodName().equals(methodName)) {
                return c.getMemo();
            }
        }
        return "未知";
    }
    public final static String getMethodName(final String memo) {
        for (HttpConfig c : values()) {
            if (c.getMemo().equals(memo)) {
                return c.getMethodName();
            }
        }
        return "";
    }
}

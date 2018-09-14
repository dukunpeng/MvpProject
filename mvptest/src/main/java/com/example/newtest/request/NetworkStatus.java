package com.example.newtest.request;

/**
 * Created by Mark on 2018/4/3.
 */

public enum NetworkStatus {
    STATUS_SUCCESS("0", "请求成功", null),
    STATUS_FAIL_PARAMS_ERROR("1", "请求参数异常", null),
    STATUS_FAIL_SERVICE_ERROR("2", "服务器异常", null),
    STATUS_FAIL_IO_EXCEPTION("2", "网络异常", null),
    STATUS_FAIL_SESSION_OUT("4", "登录超时", null),
    STATUS_FAIL_PARSING_ERROR("5", "数据解析出错", null);

    private String code;
    private String message;
    private String responseStr;

    NetworkStatus(String code, String message, String responseStr) {
        this.code = code;
        this.message = message;
        this.responseStr = responseStr;
    }

    public String getMessage() {
        return message;
    }

    public NetworkStatus setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCode() {
        return code;
    }

    public NetworkStatus setCode(String code) {
        this.code = code;
        return this;
    }

    public static NetworkStatus getNetworkStatusByCode(String code){
        NetworkStatus resultStatus = NetworkStatus.STATUS_FAIL_PARSING_ERROR;
        for(NetworkStatus status: values()){
            if(status.code.equals(code)){
                resultStatus = status;
                break;
            }
        }
        return resultStatus;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public NetworkStatus setResponseStr(String responseStr) {
        this.responseStr = responseStr;
        return this;
    }
}
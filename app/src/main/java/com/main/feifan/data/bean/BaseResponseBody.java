package com.main.feifan.data.bean;


/**
 */
public class BaseResponseBody<T> {

    private String code;//状态码
    private String msg;//消息
    private T token;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getToken() {
        return token;
    }

    public void setToken(T token) {
        this.token = token;
    }

    public boolean isOk() {
        return code.equals("200");
    }

    /*public String toMsg(String codeStr){
        int c = Integer.parseInt(codeStr);
        switch (c){
            *//*case 3005:
                return AppPl.getInstance().getString(R.string.phone_not_register);
            case 3006:
                return AppPl.getInstance().getString(R.string.phone_password_error);
            case 3007:
                return AppPl.getInstance().getString(R.string.account_disabled);
            default:
                return null;*//*
        }
    }*/

}

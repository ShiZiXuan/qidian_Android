package com.uautogo.qidian.model;

/**
 * Created by baienda on 2017/11/15.
 */

public class BaseResponse {
    /**
     * code : 0
     * msg : 成功
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

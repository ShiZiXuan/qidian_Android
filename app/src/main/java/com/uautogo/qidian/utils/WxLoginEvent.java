package com.uautogo.qidian.utils;

/**
 * Created by uuun on 2018/8/27.
 */

public class WxLoginEvent {
    private String mMsg;
    public WxLoginEvent(String msg) {
        // TODO Auto-generated constructor stub
        mMsg = msg;
    }
    public String getMsg(){
        return mMsg;
    }
}

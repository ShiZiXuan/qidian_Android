package com.uautogo.qidian.model;

/**
 * Created by uuun on 2018/8/2.
 */

public class CarMoneyBean {
    private String carMoney;
    private String code;
    private String msg;
    private String data;

    public CarMoneyBean(String carMoney, String code, String msg, String data) {
        this.carMoney = carMoney;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCarMoney() {
        return carMoney;
    }

    public void setCarMoney(String carMoney) {
        this.carMoney = carMoney;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

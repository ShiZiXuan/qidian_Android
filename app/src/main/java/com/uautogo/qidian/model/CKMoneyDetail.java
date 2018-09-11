package com.uautogo.qidian.model;

/**
 * Created by uuun on 2018/7/31.
 */

public class CKMoneyDetail {
    private String costType;
    private String time;
    private String money;

    public CKMoneyDetail(String costType, String time, String money) {
        this.costType = costType;
        this.time = time;
        this.money = money;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}

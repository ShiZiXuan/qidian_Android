package com.uautogo.qidian.model;

import java.io.Serializable;


public class BankCardInfo  implements Serializable{

    /**
     * number : 6221884210011816115
     * bank : 邮储银行
     * type : 借记卡
     */

    private String number;
    private String bank;
    private String type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
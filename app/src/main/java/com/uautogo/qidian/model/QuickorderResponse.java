package com.uautogo.qidian.model;

/**
 * Created by linjing on 2018/4/2.
 */

public class QuickorderResponse {

    private int userId;
    private String timeMeberidTransno;
    private int rid;
    private String accNo;
    private String cardHolder;
    private String idCardType;
    private String idCard;
    private String mobile;
    private String cardType;
    private String validDate;
    private String validNo;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTimeMeberidTransno() {
        return timeMeberidTransno;
    }

    public void setTimeMeberidTransno(String timeMeberidTransno) {
        this.timeMeberidTransno = timeMeberidTransno;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getValidNo() {
        return validNo;
    }

    public void setValidNo(String validNo) {
        this.validNo = validNo;
    }
}

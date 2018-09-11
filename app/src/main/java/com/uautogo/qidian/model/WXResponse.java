package com.uautogo.qidian.model;

import com.google.gson.annotations.SerializedName;

public class WXResponse extends BaseResponse {

    /**
     * data : {"appid":"wxf9a97962ab078a3a","noncestr":"gqFBHKU1F430MFap","package":"Sign=WXPay","partnerid":"1483895522","prepayid":"wx20180312152132045153ef450048760462","sign":"83995FC969EAF5F7E89388D24B949FCB","timestamp":"1520839294","_package":"Sign=WXPay","amount":0.01,"orderNumber":"1803121521340010304","title":"Etc通行付费"}
     */

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wxf9a97962ab078a3a
         * noncestr : gqFBHKU1F430MFap
         * package : Sign=WXPay
         * partnerid : 1483895522
         * prepayid : wx20180312152132045153ef450048760462
         * sign : 83995FC969EAF5F7E89388D24B949FCB
         * timestamp : 1520839294
         * _package : Sign=WXPay
         * amount : 0.01
         * orderNumber : 1803121521340010304
         * title : Etc通行付费
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;
        private String _package;
        private double amount;
        private String orderNumber;
        private String title;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String get_package() {
            return _package;
        }

        public void set_package(String _package) {
            this._package = _package;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

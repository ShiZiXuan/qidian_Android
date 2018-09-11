package com.uautogo.qidian.model;

/**
 * Created by ${ZWJ} on 2018/8/2 0002.
 */

public class CarMoney {

    /**
     * code : 0
     * msg : success
     * data : {"walletIntegral":0,"walletStatus":0,"payStatus":true,"ckeAddress":"0xec168452da1487b81729c792a3202fe15b01a562"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * walletIntegral : 0.0
         * walletStatus : 0
         * payStatus : true
         * ckeAddress : 0xec168452da1487b81729c792a3202fe15b01a562
         */

        private double walletIntegral;
        private int walletStatus;
        private boolean payStatus;
        private String ckeAddress;

        public double getWalletIntegral() {
            return walletIntegral;
        }

        public void setWalletIntegral(double walletIntegral) {
            this.walletIntegral = walletIntegral;
        }

        public int getWalletStatus() {
            return walletStatus;
        }

        public void setWalletStatus(int walletStatus) {
            this.walletStatus = walletStatus;
        }

        public boolean isPayStatus() {
            return payStatus;
        }

        public void setPayStatus(boolean payStatus) {
            this.payStatus = payStatus;
        }

        public String getCkeAddress() {
            return ckeAddress;
        }

        public void setCkeAddress(String ckeAddress) {
            this.ckeAddress = ckeAddress;
        }
    }
}

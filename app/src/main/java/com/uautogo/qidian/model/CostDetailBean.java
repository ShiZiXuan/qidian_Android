package com.uautogo.qidian.model;

import java.util.List;

/**
 * Created by uuun on 2018/8/3.
 */

public class CostDetailBean {

    /**
     * code : 0
     * msg : success
     * data : {"list":[{"ckeType":1,"payTxHash":"","trsDate":201808,"trsType":4,"fromAddress":"","id":11,"trsCreateTime":"2018-08-02 15:36:12.0","ckeId":1,"toAddress":"","userId":375,"trsNum":100},{"ckeType":1,"payTxHash":"","trsDate":201808,"trsType":3,"fromAddress":"","id":14,"trsCreateTime":"2018-08-02 19:10:21.0","ckeId":1,"toAddress":"","userId":375,"trsNum":100},{"ckeType":0,"payTxHash":"","trsDate":201808,"trsType":0,"fromAddress":"","id":15,"trsCreateTime":"2018-08-02 23:50:43.0","ckeId":1,"toAddress":"","userId":375,"trsNum":0.00333},{"ckeType":0,"payTxHash":"","trsDate":201808,"trsType":0,"fromAddress":"","id":16,"trsCreateTime":"2018-08-03 00:35:47.0","ckeId":1,"toAddress":"","userId":375,"trsNum":0.00303},{"ckeType":0,"payTxHash":"","trsDate":201808,"trsType":0,"fromAddress":"","id":17,"trsCreateTime":"2018-08-03 00:42:06.0","ckeId":1,"toAddress":"","userId":375,"trsNum":0.00303}],"pn":1,"pSize":20,"pageCount":1}
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
         * list : [{"ckeType":1,"payTxHash":"","trsDate":201808,"trsType":4,"fromAddress":"","id":11,"trsCreateTime":"2018-08-02 15:36:12.0","ckeId":1,"toAddress":"","userId":375,"trsNum":100},{"ckeType":1,"payTxHash":"","trsDate":201808,"trsType":3,"fromAddress":"","id":14,"trsCreateTime":"2018-08-02 19:10:21.0","ckeId":1,"toAddress":"","userId":375,"trsNum":100},{"ckeType":0,"payTxHash":"","trsDate":201808,"trsType":0,"fromAddress":"","id":15,"trsCreateTime":"2018-08-02 23:50:43.0","ckeId":1,"toAddress":"","userId":375,"trsNum":0.00333},{"ckeType":0,"payTxHash":"","trsDate":201808,"trsType":0,"fromAddress":"","id":16,"trsCreateTime":"2018-08-03 00:35:47.0","ckeId":1,"toAddress":"","userId":375,"trsNum":0.00303},{"ckeType":0,"payTxHash":"","trsDate":201808,"trsType":0,"fromAddress":"","id":17,"trsCreateTime":"2018-08-03 00:42:06.0","ckeId":1,"toAddress":"","userId":375,"trsNum":0.00303}]
         * pn : 1
         * pSize : 20
         * pageCount : 1
         */

        private int pn;
        private int pSize;
        private int pageCount;
        private List<ListBean> list;

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getPSize() {
            return pSize;
        }

        public void setPSize(int pSize) {
            this.pSize = pSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * ckeType : 1
             * payTxHash :
             * trsDate : 201808
             * trsType : 4
             * fromAddress :
             * id : 11
             * trsCreateTime : 2018-08-02 15:36:12.0
             * ckeId : 1
             * toAddress :
             * userId : 375
             * trsNum : 100.0
             */

            private int ckeType;
            private String payTxHash;
            private int trsDate;
            private int trsType;
            private String fromAddress;
            private int id;
            private String trsCreateTime;
            private int ckeId;
            private String toAddress;
            private int userId;
            private double trsNum;

            public int getCkeType() {
                return ckeType;
            }

            public void setCkeType(int ckeType) {
                this.ckeType = ckeType;
            }

            public String getPayTxHash() {
                return payTxHash;
            }

            public void setPayTxHash(String payTxHash) {
                this.payTxHash = payTxHash;
            }

            public int getTrsDate() {
                return trsDate;
            }

            public void setTrsDate(int trsDate) {
                this.trsDate = trsDate;
            }

            public int getTrsType() {
                return trsType;
            }

            public void setTrsType(int trsType) {
                this.trsType = trsType;
            }

            public String getFromAddress() {
                return fromAddress;
            }

            public void setFromAddress(String fromAddress) {
                this.fromAddress = fromAddress;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTrsCreateTime() {
                return trsCreateTime;
            }

            public void setTrsCreateTime(String trsCreateTime) {
                this.trsCreateTime = trsCreateTime;
            }

            public int getCkeId() {
                return ckeId;
            }

            public void setCkeId(int ckeId) {
                this.ckeId = ckeId;
            }

            public String getToAddress() {
                return toAddress;
            }

            public void setToAddress(String toAddress) {
                this.toAddress = toAddress;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public double getTrsNum() {
                return trsNum;
            }

            public void setTrsNum(double trsNum) {
                this.trsNum = trsNum;
            }
        }
    }
}

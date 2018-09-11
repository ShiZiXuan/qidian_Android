package com.uautogo.qidian.model;

import java.util.List;

/**
 * Created by uuun on 2018/8/15.
 */

public class NewETCResponse2 {

    /**
     * code : 0
     * msg : success
     * data : {"list":[{"amount":475,"orderNumber":"HHHDDDEEFFF","trsDate":"2018-06-20 18:24","createTime":1529555701000,"trsTime":"2018-06-20 18:24:07","id":176,"type":0,"userId":375,"status":1},{"amount":101,"orderNumber":"AAABBBCCCDDD","trsDate":"2018-06-15 14:39","createTime":1528950905749,"trsTime":"2018-06-15 14:39:55","id":121,"type":0,"userId":375,"status":1}],"countNum":2,"sumAmount":5.76,"pn":1,"pSize":20,"pageCount":1}
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
         * list : [{"amount":475,"orderNumber":"HHHDDDEEFFF","trsDate":"2018-06-20 18:24","createTime":1529555701000,"trsTime":"2018-06-20 18:24:07","id":176,"type":0,"userId":375,"status":1},{"amount":101,"orderNumber":"AAABBBCCCDDD","trsDate":"2018-06-15 14:39","createTime":1528950905749,"trsTime":"2018-06-15 14:39:55","id":121,"type":0,"userId":375,"status":1}]
         * countNum : 2
         * sumAmount : 5.76
         * pn : 1
         * pSize : 20
         * pageCount : 1
         */

        private int countNum;
        private double sumAmount;
        private int pn;
        private int pSize;
        private int pageCount;
        private List<ListBean> list;

        public int getCountNum() {
            return countNum;
        }

        public void setCountNum(int countNum) {
            this.countNum = countNum;
        }

        public double getSumAmount() {
            return sumAmount;
        }

        public void setSumAmount(double sumAmount) {
            this.sumAmount = sumAmount;
        }

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
             * amount : 475.0
             * orderNumber : HHHDDDEEFFF
             * trsDate : 2018-06-20 18:24
             * createTime : 1529555701000
             * trsTime : 2018-06-20 18:24:07
             * id : 176
             * type : 0
             * userId : 375
             * status : 1
             */

            private double amount;
            private String orderNumber;
            private String trsDate;
            private long createTime;
            private String trsTime;
            private int id;
            private int type;
            private int userId;
            private int status;

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

            public String getTrsDate() {
                return trsDate;
            }

            public void setTrsDate(String trsDate) {
                this.trsDate = trsDate;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getTrsTime() {
                return trsTime;
            }

            public void setTrsTime(String trsTime) {
                this.trsTime = trsTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}

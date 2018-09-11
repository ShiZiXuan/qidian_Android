package com.uautogo.qidian.model;

import java.io.Serializable;
import java.util.List;



public class NewETCResponse  {

    /**
     * data : [{"id":1,"userId":499,"amount":5000,"orderNumber":"555555558888888","trsDate":"2018.03.01","trsTime":"2018.03.01","type":-1,"status":0,"createTime":1519697117487},{"id":3,"userId":499,"amount":5000,"orderNumber":"555555558888876","trsDate":"2018.03.01","trsTime":"2018.03.01","type":-1,"status":1,"createTime":1519697167487},{"id":2,"userId":499,"amount":5000,"orderNumber":"555555558888877","trsDate":"2018.03.01","trsTime":"2018.03.01","type":-1,"status":1,"createTime":1519697107487}]
     * code : 0
     * httpStatus : 200
     * path : null
     * timestamp : 1519980705180
     * exception : null
     */

    private int code;
    private int httpStatus;
    private Object path;
    private long timestamp;
    private Object exception;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * userId : 499
         * amount : 5000
         * orderNumber : 555555558888888
         * trsDate : 2018.03.01
         * trsTime : 2018.03.01
         * type : -1
         * status : 0
         * createTime : 1519697117487
         */

        private int id;
        private int userId;
        private int amount;
        private String orderNumber;
        private String trsDate;
        private String trsTime;
        private int type;
        private int status;
        private long createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
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

        public String getTrsTime() {
            return trsTime;
        }

        public void setTrsTime(String trsTime) {
            this.trsTime = trsTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}

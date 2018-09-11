package com.uautogo.qidian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Serenade on 2017/8/31.
 */

public class OrderRespons {
    public List<Order> data;

    public class Order implements Serializable {
        /**
         * amount : 1
         * channel : alipay
         * clientIp : 127.0.0.1
         * ctime : 1498736527000
         * currency : cny
         * description :
         * expireTime : 1498822928000
         * expressNo :
         * id : 10000017
         * mtime : 1498736528000
         * receiverAddress : 中关村南大街9号，理工科技大厦1411
         * receiverCity : 北京市
         * receiverDistrict : 海淀区
         * receiverMobile : 18772985316
         * receiverName : 张宇
         * receiverProvince : 北京市
         * status : active
         * transactionId : ch_ujPur5LCKSqDDGevnL5KCOG4
         * uid : 9
         */

        private double amount;
        private String channel;
        private String clientIp;
        private long ctime;
        private long payTime;
        private String currency;
        private String description;
        private long expireTime;
        private String expressNo;
        private int id;
        private long mtime;
        private String receiverAddress;
        private String receiverCity;
        private String receiverDistrict;
        private String receiverMobile;
        private String receiverName;
        private String receiverProvince;
        private String status;
        private String transactionId;
        private int uid;
        private  String orderTitle;
        private   int refundStatus;

        public int getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(int refundStatus) {
            this.refundStatus = refundStatus;
        }

        public String getOrderTitle() {
            return orderTitle;
        }

        public void setOrderTitle(String orderTitle) {
            this.orderTitle = orderTitle;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
            this.ctime = ctime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }

        public String getExpressNo() {
            return expressNo;
        }

        public void setExpressNo(String expressNo) {
            this.expressNo = expressNo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getMtime() {
            return mtime;
        }

        public void setMtime(long mtime) {
            this.mtime = mtime;
        }

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public String getReceiverCity() {
            return receiverCity;
        }

        public void setReceiverCity(String receiverCity) {
            this.receiverCity = receiverCity;
        }

        public String getReceiverDistrict() {
            return receiverDistrict;
        }

        public void setReceiverDistrict(String receiverDistrict) {
            this.receiverDistrict = receiverDistrict;
        }

        public String getReceiverMobile() {
            return receiverMobile;
        }

        public void setReceiverMobile(String receiverMobile) {
            this.receiverMobile = receiverMobile;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverProvince() {
            return receiverProvince;
        }

        public void setReceiverProvince(String receiverProvince) {
            this.receiverProvince = receiverProvince;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public long getPayTime() {
            return payTime;
        }

        public void setPayTime(long payTime) {
            this.payTime = payTime;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "amount=" + amount +
                    ", channel='" + channel + '\'' +
                    ", clientIp='" + clientIp + '\'' +
                    ", ctime=" + ctime +
                    ", payTime=" + payTime +
                    ", currency='" + currency + '\'' +
                    ", description='" + description + '\'' +
                    ", expireTime=" + expireTime +
                    ", expressNo='" + expressNo + '\'' +
                    ", id=" + id +
                    ", mtime=" + mtime +
                    ", receiverAddress='" + receiverAddress + '\'' +
                    ", receiverCity='" + receiverCity + '\'' +
                    ", receiverDistrict='" + receiverDistrict + '\'' +
                    ", receiverMobile='" + receiverMobile + '\'' +
                    ", receiverName='" + receiverName + '\'' +
                    ", receiverProvince='" + receiverProvince + '\'' +
                    ", status='" + status + '\'' +
                    ", transactionId='" + transactionId + '\'' +
                    ", uid=" + uid +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderRespons{" +
                "data=" + data +
                '}';
    }
}

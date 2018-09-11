package com.uautogo.qidian.model;

import java.io.Serializable;

/**
 * Created by linjing on 2017/11/15.
 */

public class EtcInfoResponse {

    /**
     * code : 0
     * data : {"auditStatus":1,"bankCardImg":"/bankCardImg.img","bankCardImgUrl":"http://image.uautogo.com/bankCardImg.img","createTime":"2017111318244124","driverLicenseImg":"/driverLicenseImg.img","driverLicenseImgUrl":"http://image.uautogo.com/driverLicenseImg.img","gmtCreate":1510569684000,"idCardBackImg":"/idCardBackImg.img","idCardBackImgUrl":"http://image.uautogo.com/idCardBackImg.img","idCardFrontImg":"/idCardFrontImg.img","idCardFrontImgUrl":"http://image.uautogo.com/idCardFrontImg.img","userId":1,"vtlCopyImg":"/vtlCopyImg.img","vtlCopyImgUrl":"http://image.uautogo.com/vtlCopyImg.img","vtlOriginalImg":"/vtlOriginalImg.img","vtlOriginalImgUrl":"http://image.uautogo.com/vtlOriginalImg.img"}
     * msg : success
     */

    private int code;
    private Data data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class Data implements Serializable{
        /**
         * auditStatus : 1
         * bankCardImg : /bankCardImg.img
         * bankCardImgUrl : http://image.uautogo.com/bankCardImg.img
         * createTime : 2017111318244124
         * driverLicenseImg : /driverLicenseImg.img
         * driverLicenseImgUrl : http://image.uautogo.com/driverLicenseImg.img
         * gmtCreate : 1510569684000
         * idCardBackImg : /idCardBackImg.img
         * idCardBackImgUrl : http://image.uautogo.com/idCardBackImg.img
         * idCardFrontImg : /idCardFrontImg.img
         * idCardFrontImgUrl : http://image.uautogo.com/idCardFrontImg.img
         * userId : 1
         * vtlCopyImg : /vtlCopyImg.img
         * vtlCopyImgUrl : http://image.uautogo.com/vtlCopyImg.img
         * vtlOriginalImg : /vtlOriginalImg.img
         * vtlOriginalImgUrl : http://image.uautogo.com/vtlOriginalImg.img
         */

        private int auditStatus=-1;
        private String bankCardImg;
        private String bankCardImgUrl;
        private String createTime;
        private String driverLicenseImg;
        private String driverLicenseImgUrl;
        private long gmtCreate;
        private String idCardBackImg;
        private String idCardBackImgUrl;
        private String idCardFrontImg;
        private String idCardFrontImgUrl;
        private int userId;
        private String vtlCopyImg;
        private String vtlCopyImgUrl;
        private String vtlOriginalImg;
        private String vtlOriginalImgUrl;
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getBankCardImg() {
            return bankCardImg;
        }

        public void setBankCardImg(String bankCardImg) {
            this.bankCardImg = bankCardImg;
        }

        public String getBankCardImgUrl() {
            return bankCardImgUrl;
        }

        public void setBankCardImgUrl(String bankCardImgUrl) {
            this.bankCardImgUrl = bankCardImgUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDriverLicenseImg() {
            return driverLicenseImg;
        }

        public void setDriverLicenseImg(String driverLicenseImg) {
            this.driverLicenseImg = driverLicenseImg;
        }

        public String getDriverLicenseImgUrl() {
            return driverLicenseImgUrl;
        }

        public void setDriverLicenseImgUrl(String driverLicenseImgUrl) {
            this.driverLicenseImgUrl = driverLicenseImgUrl;
        }

        public long getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(long gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getIdCardBackImg() {
            return idCardBackImg;
        }

        public void setIdCardBackImg(String idCardBackImg) {
            this.idCardBackImg = idCardBackImg;
        }

        public String getIdCardBackImgUrl() {
            return idCardBackImgUrl;
        }

        public void setIdCardBackImgUrl(String idCardBackImgUrl) {
            this.idCardBackImgUrl = idCardBackImgUrl;
        }

        public String getIdCardFrontImg() {
            return idCardFrontImg;
        }

        public void setIdCardFrontImg(String idCardFrontImg) {
            this.idCardFrontImg = idCardFrontImg;
        }

        public String getIdCardFrontImgUrl() {
            return idCardFrontImgUrl;
        }

        public void setIdCardFrontImgUrl(String idCardFrontImgUrl) {
            this.idCardFrontImgUrl = idCardFrontImgUrl;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getVtlCopyImg() {
            return vtlCopyImg;
        }

        public void setVtlCopyImg(String vtlCopyImg) {
            this.vtlCopyImg = vtlCopyImg;
        }

        public String getVtlCopyImgUrl() {
            return vtlCopyImgUrl;
        }

        public void setVtlCopyImgUrl(String vtlCopyImgUrl) {
            this.vtlCopyImgUrl = vtlCopyImgUrl;
        }

        public String getVtlOriginalImg() {
            return vtlOriginalImg;
        }

        public void setVtlOriginalImg(String vtlOriginalImg) {
            this.vtlOriginalImg = vtlOriginalImg;
        }

        public String getVtlOriginalImgUrl() {
            return vtlOriginalImgUrl;
        }

        public void setVtlOriginalImgUrl(String vtlOriginalImgUrl) {
            this.vtlOriginalImgUrl = vtlOriginalImgUrl;
        }
    }
}

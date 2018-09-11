package com.uautogo.qidian.model;

import java.util.List;

/**
 * Created by uuun on 2018/8/21.
 */

public class MyCarBean {


    /**
     * code : 0
     * msg : success
     * data : [{"id":546,"bidUserId":375,"bidCarNumber":"陕A6Z171","bidCarDistinguishCode":"WBA1B1108CF379615","bidAddr":"山省西密市桥村区庄虞市路八十七号1号楼41","bidUseCharacter":"非营运","bidIssueDate":"20120911","bidModel":"NWBA35110","bidOwner":"卫超","bidEngineNumber":"A6270231N20","bidVehicleType":"小型轿车","bidTravelCreateTime":"20120911","bidImageUrl":"http://image.uautogo.com/etc/etcImage/20180816/16/0d92857f-d504-4cf8-9d3b-53237f65bad4.jpg|http://image.uautogo.com/etc/etcImage/20180816/16/b035ca04-eb7b-4cc7-b255-30881500f705.jpg|http://image.uautogo.com/etc/etcImage/20180816/16/05fbaa9d-f0d9-4e47-9abc-8e332a55d4b8.jpg","bidAuditEtcStatus":-1,"bidAuditEtcReason":"","etcType":0,"etcBuyStatus":0,"createTime":1534409159804},{"id":534,"bidUserId":375,"bidCarNumber":"豫NSY953","bidCarDistinguishCode":"LGBH52E04GY502068","bidAddr":"河南省夏邑县何营乡政府路502号","bidUseCharacter":"非型专项客","bidIssueDate":"20160715","bidModel":"东风日产牌DFL7168VBL2","bidOwner":"刘欣","bidEngineNumber":"984359X","bidVehicleType":"小型轿车","bidTravelCreateTime":"20160718","bidImageUrl":"http://image.uautogo.com/etc/etcImage/20180804/14/f211b8fb-2193-4c29-bce3-208d6360ba43.jpg|http://image.uautogo.com/etc/etcImage/20180804/14/6ad7f253-a65b-4129-a99b-bb1605933aa1.jpg|http://image.uautogo.com/etc/etcImage/20180804/14/566ce612-c843-40ce-a452-bf6121f810ce.jpg","bidAuditEtcStatus":0,"bidAuditEtcReason":"","etcType":4,"etcBuyStatus":1,"createTime":1533364755532}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 546
         * bidUserId : 375
         * bidCarNumber : 陕A6Z171
         * bidCarDistinguishCode : WBA1B1108CF379615
         * bidAddr : 山省西密市桥村区庄虞市路八十七号1号楼41
         * bidUseCharacter : 非营运
         * bidIssueDate : 20120911
         * bidModel : NWBA35110
         * bidOwner : 卫超
         * bidEngineNumber : A6270231N20
         * bidVehicleType : 小型轿车
         * bidTravelCreateTime : 20120911
         * bidImageUrl : http://image.uautogo.com/etc/etcImage/20180816/16/0d92857f-d504-4cf8-9d3b-53237f65bad4.jpg|http://image.uautogo.com/etc/etcImage/20180816/16/b035ca04-eb7b-4cc7-b255-30881500f705.jpg|http://image.uautogo.com/etc/etcImage/20180816/16/05fbaa9d-f0d9-4e47-9abc-8e332a55d4b8.jpg
         * bidAuditEtcStatus : -1
         * bidAuditEtcReason :
         * etcType : 0
         * etcBuyStatus : 0
         * createTime : 1534409159804
         */

        private int id;
        private int bidUserId;
        private String bidCarNumber;
        private String bidCarDistinguishCode;
        private String bidAddr;
        private String bidUseCharacter;
        private String bidIssueDate;
        private String bidModel;
        private String bidOwner;
        private String bidEngineNumber;
        private String bidVehicleType;
        private String bidTravelCreateTime;
        private String bidImageUrl;
        private int bidAuditEtcStatus;
        private String bidAuditEtcReason;
        private int etcType;
        private int etcBuyStatus;
        private long createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBidUserId() {
            return bidUserId;
        }

        public void setBidUserId(int bidUserId) {
            this.bidUserId = bidUserId;
        }

        public String getBidCarNumber() {
            return bidCarNumber;
        }

        public void setBidCarNumber(String bidCarNumber) {
            this.bidCarNumber = bidCarNumber;
        }

        public String getBidCarDistinguishCode() {
            return bidCarDistinguishCode;
        }

        public void setBidCarDistinguishCode(String bidCarDistinguishCode) {
            this.bidCarDistinguishCode = bidCarDistinguishCode;
        }

        public String getBidAddr() {
            return bidAddr;
        }

        public void setBidAddr(String bidAddr) {
            this.bidAddr = bidAddr;
        }

        public String getBidUseCharacter() {
            return bidUseCharacter;
        }

        public void setBidUseCharacter(String bidUseCharacter) {
            this.bidUseCharacter = bidUseCharacter;
        }

        public String getBidIssueDate() {
            return bidIssueDate;
        }

        public void setBidIssueDate(String bidIssueDate) {
            this.bidIssueDate = bidIssueDate;
        }

        public String getBidModel() {
            return bidModel;
        }

        public void setBidModel(String bidModel) {
            this.bidModel = bidModel;
        }

        public String getBidOwner() {
            return bidOwner;
        }

        public void setBidOwner(String bidOwner) {
            this.bidOwner = bidOwner;
        }

        public String getBidEngineNumber() {
            return bidEngineNumber;
        }

        public void setBidEngineNumber(String bidEngineNumber) {
            this.bidEngineNumber = bidEngineNumber;
        }

        public String getBidVehicleType() {
            return bidVehicleType;
        }

        public void setBidVehicleType(String bidVehicleType) {
            this.bidVehicleType = bidVehicleType;
        }

        public String getBidTravelCreateTime() {
            return bidTravelCreateTime;
        }

        public void setBidTravelCreateTime(String bidTravelCreateTime) {
            this.bidTravelCreateTime = bidTravelCreateTime;
        }

        public String getBidImageUrl() {
            return bidImageUrl;
        }

        public void setBidImageUrl(String bidImageUrl) {
            this.bidImageUrl = bidImageUrl;
        }

        public int getBidAuditEtcStatus() {
            return bidAuditEtcStatus;
        }

        public void setBidAuditEtcStatus(int bidAuditEtcStatus) {
            this.bidAuditEtcStatus = bidAuditEtcStatus;
        }

        public String getBidAuditEtcReason() {
            return bidAuditEtcReason;
        }

        public void setBidAuditEtcReason(String bidAuditEtcReason) {
            this.bidAuditEtcReason = bidAuditEtcReason;
        }

        public int getEtcType() {
            return etcType;
        }

        public void setEtcType(int etcType) {
            this.etcType = etcType;
        }

        public int getEtcBuyStatus() {
            return etcBuyStatus;
        }

        public void setEtcBuyStatus(int etcBuyStatus) {
            this.etcBuyStatus = etcBuyStatus;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}

package com.uautogo.qidian.model;

import java.util.List;

/**
 * Created by Jeremy on 2017/5/28.
 */

public class CarInfo extends BaseQidianRespons {
    public List<Car> data;

    public static class Car {
        private Integer userId;
        private String nickName, provinceCode, engineNo, hphm, jszz, vin;
        private Long ctime;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getEngineNo() {
            return engineNo;
        }

        public void setEngineNo(String engineNo) {
            this.engineNo = engineNo;
        }

        public String getHphm() {
            return hphm;
        }

        public void setHphm(String hphm) {
            this.hphm = hphm;
        }

        public String getJszz() {
            return jszz;
        }

        public void setJszz(String jszz) {
            this.jszz = jszz;
        }

        public String getVin() {
            return vin;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }

        public Long getCtime() {
            return ctime;
        }

        public void setCtime(Long ctime) {
            this.ctime = ctime;
        }
    }
}

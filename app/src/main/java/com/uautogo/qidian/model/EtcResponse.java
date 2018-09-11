package com.uautogo.qidian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linjing on 2017/11/7.
 */


public class EtcResponse implements Serializable{

    /**
     * code : 0
     * data : {"carDvrs":[{"dvrId":"dvr002","etcs":[{"distance":23.34,"drivingStatus":1,"duration":"2小时0分7秒","entryAddress":"北京市清河高速口","entryTime":"20171108074134","exitAddress":"北京市回龙观高速1口","exitTime":"20171108094141","expense":100.49,"payStatus":0},{"distance":23.34,"drivingStatus":1,"duration":"48分36秒","entryAddress":"北京市清河高速口","entryTime":"20171107084235","exitAddress":"北京市回龙观高速1口","exitTime":"20171107093111","expense":5,"payStatus":1}],"plateNum":""}],"displayName":"天天向上","userId":2}
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

    public static class Data {
        /**
         * carDvrs : [{"dvrId":"dvr002","etcs":[{"distance":23.34,"drivingStatus":1,"duration":"2小时0分7秒","entryAddress":"北京市清河高速口","entryTime":"20171108074134","exitAddress":"北京市回龙观高速1口","exitTime":"20171108094141","expense":100.49,"payStatus":0},{"distance":23.34,"drivingStatus":1,"duration":"48分36秒","entryAddress":"北京市清河高速口","entryTime":"20171107084235","exitAddress":"北京市回龙观高速1口","exitTime":"20171107093111","expense":5,"payStatus":1}],"plateNum":""}]
         * displayName : 天天向上
         * userId : 2
         */

        private String displayName;
        private int userId;
        private List<CarDvrs> carDvrs;

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<CarDvrs> getCarDvrs() {
            return carDvrs;
        }

        public void setCarDvrs(List<CarDvrs> carDvrs) {
            this.carDvrs = carDvrs;
        }

        public static class CarDvrs {
            /**
             * dvrId : dvr002
             * etcs : [{"distance":23.34,"drivingStatus":1,"duration":"2小时0分7秒","entryAddress":"北京市清河高速口","entryTime":"20171108074134","exitAddress":"北京市回龙观高速1口","exitTime":"20171108094141","expense":100.49,"payStatus":0},{"distance":23.34,"drivingStatus":1,"duration":"48分36秒","entryAddress":"北京市清河高速口","entryTime":"20171107084235","exitAddress":"北京市回龙观高速1口","exitTime":"20171107093111","expense":5,"payStatus":1}]
             * plateNum :
             */

            private String dvrId;
            private String plateNum;
            private List<Etcs> etcs;

            public String getDvrId() {
                return dvrId;
            }

            public void setDvrId(String dvrId) {
                this.dvrId = dvrId;
            }

            public String getPlateNum() {
                return plateNum;
            }

            public void setPlateNum(String plateNum) {
                this.plateNum = plateNum;
            }

            public List<Etcs> getEtcs() {
                return etcs;
            }

            public void setEtcs(List<Etcs> etcs) {
                this.etcs = etcs;
            }

            public static class Etcs implements  Serializable{
                /**
                 * distance : 23.34
                 * drivingStatus : 1
                 * duration : 2小时0分7秒
                 * entryAddress : 北京市清河高速口
                 * entryTime : 20171108074134
                 * exitAddress : 北京市回龙观高速1口
                 * exitTime : 20171108094141
                 * expense : 100.49
                 * payStatus : 0
                 */

                private double distance;
                private int drivingStatus;
                private String duration;
                private String entryAddress;
                private String entryTime;
                private String exitAddress;
                private String exitTime;
                private double expense;
                private int payStatus;
                private int orderId;

                public int getOrderId() {
                    return orderId;
                }

                public void setOrderId(int orderId) {
                    this.orderId = orderId;
                }

                public double getDistance() {
                    return distance;
                }

                public void setDistance(double distance) {
                    this.distance = distance;
                }

                public int getDrivingStatus() {
                    return drivingStatus;
                }

                public void setDrivingStatus(int drivingStatus) {
                    this.drivingStatus = drivingStatus;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getEntryAddress() {
                    return entryAddress;
                }

                public void setEntryAddress(String entryAddress) {
                    this.entryAddress = entryAddress;
                }

                public String getEntryTime() {
                    return entryTime;
                }

                public void setEntryTime(String entryTime) {
                    this.entryTime = entryTime;
                }

                public String getExitAddress() {
                    return exitAddress;
                }

                public void setExitAddress(String exitAddress) {
                    this.exitAddress = exitAddress;
                }

                public String getExitTime() {
                    return exitTime;
                }

                public void setExitTime(String exitTime) {
                    this.exitTime = exitTime;
                }

                public double getExpense() {
                    return expense;
                }

                public void setExpense(double expense) {
                    this.expense = expense;
                }

                public int getPayStatus() {
                    return payStatus;
                }

                public void setPayStatus(int payStatus) {
                    this.payStatus = payStatus;
                }
            }
        }
    }
}



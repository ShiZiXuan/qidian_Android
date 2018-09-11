package com.uautogo.qidian.model;

import java.io.Serializable;

/**
 * Created by linjing on 2018/1/30.
 */

public class MoveCarRespons  implements Serializable{


    /**
     * status : 0
     * msg : ok
     * result : {"number":"京AG6096","color":"蓝","type":"1","score":"91","position":"231_374_380_405"}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * number : 京AG6096
         * color : 蓝
         * type : 1
         * score : 91
         * position : 231_374_380_405
         */

        private String number;
        private String color;
        private String type;
        private String score;
        private String position;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}

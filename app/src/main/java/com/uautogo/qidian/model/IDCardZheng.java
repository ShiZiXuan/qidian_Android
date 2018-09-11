package com.uautogo.qidian.model;


import java.io.Serializable;

public class IDCardZheng implements Serializable{

    /**
     * address : 河燕省温姓武德怯马冯吝村王50号韵号
     * birth : 19861105
     * config_str : {"side":"face"}
     * face_rect : {"angle":-90,"center":{"x":175.5,"y":80.5},"size":{"height":37,"width":33}}
     * name : 王敏
     * nationality : 汉
     * num : 410825198611055531
     * request_id : 20180115133008_66fa180522f6353654d3e7e0a7a37f3b
     * sex : 男
     * success : true
     */

    private String address;
    private String birth;
    private String config_str;

    private String name;
    private String nationality;
    private String num;
    private String request_id;
    private String sex;
    private boolean success;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getConfig_str() {
        return config_str;
    }

    public void setConfig_str(String config_str) {
        this.config_str = config_str;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class FaceRectBean implements  Serializable{
        /**
         * angle : -90
         * center : {"x":175.5,"y":80.5}
         * size : {"height":37,"width":33}
         */

        private int angle;
        private CenterBean center;
        private SizeBean size;

        public int getAngle() {
            return angle;
        }

        public void setAngle(int angle) {
            this.angle = angle;
        }

        public CenterBean getCenter() {
            return center;
        }

        public void setCenter(CenterBean center) {
            this.center = center;
        }

        public SizeBean getSize() {
            return size;
        }

        public void setSize(SizeBean size) {
            this.size = size;
        }

        public static class CenterBean  implements  Serializable {
            /**
             * x : 175.5
             * y : 80.5
             */

            private double x;
            private double y;

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }
        }

        public static class SizeBean implements  Serializable {
            /**
             * height : 37
             * width : 33
             */

            private int height;
            private int width;

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }
        }
    }
}

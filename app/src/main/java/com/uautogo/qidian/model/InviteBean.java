package com.uautogo.qidian.model;

import java.util.List;

/**
 * Created by uuun on 2018/8/20.
 */

public class InviteBean {

    /**
     * code : 0
     * msg : success
     * data : {"list":[{"createTime":"2018-08-30 17:34:38.0","userAvatar":"","id":539,"userName":"151****9710","userId":7164,"rUserId":5276,"status":0}],"countNum":1,"pn":1,"pSize":20,"pageCount":1}
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
         * list : [{"createTime":"2018-08-30 17:34:38.0","userAvatar":"","id":539,"userName":"151****9710","userId":7164,"rUserId":5276,"status":0}]
         * countNum : 1
         * pn : 1
         * pSize : 20
         * pageCount : 1
         */

        private int countNum;
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
             * createTime : 2018-08-30 17:34:38.0
             * userAvatar :
             * id : 539
             * userName : 151****9710
             * userId : 7164
             * rUserId : 5276
             * status : 0
             */

            private String createTime;
            private String userAvatar;
            private int id;
            private String userName;
            private int userId;
            private int rUserId;
            private int status;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUserAvatar() {
                return userAvatar;
            }

            public void setUserAvatar(String userAvatar) {
                this.userAvatar = userAvatar;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getRUserId() {
                return rUserId;
            }

            public void setRUserId(int rUserId) {
                this.rUserId = rUserId;
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

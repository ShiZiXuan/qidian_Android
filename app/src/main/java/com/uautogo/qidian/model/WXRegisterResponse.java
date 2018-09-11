package com.uautogo.qidian.model;

/**
 * 作者：Serenade
 * 邮箱：SerenadeHL@163.com
 * 创建时间：2018-03-22 上午10:32:00
 */
public class WXRegisterResponse extends BaseResponse {

    /**
     * data : {"auth":{"expireTime":1537237584726,"token":"8cd7a3e6ca9e47ecacef08ef5c331469"},"user":{"avatar":"http://47.93.58.39/upload/qidian/avatar/20170625/14/4df599b3-f193-48a9-8d3c-a5b2241084cf","id":4,"nickname":"138****8888"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * auth : {"expireTime":1537237584726,"token":"8cd7a3e6ca9e47ecacef08ef5c331469"}
         * user : {"avatar":"http://47.93.58.39/upload/qidian/avatar/20170625/14/4df599b3-f193-48a9-8d3c-a5b2241084cf","id":4,"nickname":"138****8888"}
         */

        private AuthBean auth;
        private UserBean user;
        public AuthBean getAuth() {
            return auth;
        }

        public void setAuth(AuthBean auth) {
            this.auth = auth;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class AuthBean {
            /**
             * expireTime : 1537237584726
             * token : 8cd7a3e6ca9e47ecacef08ef5c331469
             */

            private long expireTime;
            private String token;


            public long getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(long expireTime) {
                this.expireTime = expireTime;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }

        public static class UserBean {
            /**
             * avatar : http://47.93.58.39/upload/qidian/avatar/20170625/14/4df599b3-f193-48a9-8d3c-a5b2241084cf
             * id : 4
             * nickname : 138****8888
             */

            private String avatar;
            private int id;
            private String nickname;
            private String mobile;
            private  String  openIdXcx;

            public String getOpenIdXcx() {
                return openIdXcx;
            }

            public void setOpenIdXcx(String openIdXcx) {
                this.openIdXcx = openIdXcx;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}

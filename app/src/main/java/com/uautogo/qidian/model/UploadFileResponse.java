package com.uautogo.qidian.model;

/**
 * Created by baienda on 2017/11/15.
 */

public class UploadFileResponse {

    /**
     * code : 0
     * msg : 成功
     * data : {"url":"/img/avatar/20170615/01/79fb732e-a094-4ee2-a9cd-e1bdc1f2760e.png"}
     */

    private int code;
    private String msg;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        /**
         * url : /img/avatar/20170615/01/79fb732e-a094-4ee2-a9cd-e1bdc1f2760e.png
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

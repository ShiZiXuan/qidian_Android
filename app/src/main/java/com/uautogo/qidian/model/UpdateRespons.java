package com.uautogo.qidian.model;

/**
 * Created by Jeremy on 2017/5/28.
 */

public class UpdateRespons extends BaseQidianRespons{
    public Data data;

    public class Data{
        public int updateFlag;
        public String  versionNo;
        public String updateUrl;
    }

}

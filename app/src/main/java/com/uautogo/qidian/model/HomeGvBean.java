package com.uautogo.qidian.model;

/**
 * Created by uuun on 2018/8/10.
 */

public class HomeGvBean {
    private String name;
    private int iv;

    public HomeGvBean(String name, int iv) {
        this.name = name;
        this.iv = iv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIv() {
        return iv;
    }

    public void setIv(int iv) {
        this.iv = iv;
    }
}

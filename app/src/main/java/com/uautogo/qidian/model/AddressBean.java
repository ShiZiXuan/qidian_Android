package com.uautogo.qidian.model;

import java.io.Serializable;

/**
 * Created by uuun on 2018/8/7.
 */

public class AddressBean implements Serializable {
    private String name;
    private String address;
    private String phone;
    private String addressDetail;

    public AddressBean(String name, String address, String phone, String addressDetail) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.addressDetail = addressDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}

package com.uautogo.qidian.model;

import java.io.Serializable;

public class DriveCardInfo  implements Serializable{

    /**
     * addr : 山省西密市桥村区庄虞市路八十七号1号楼41
     * config_str : {"side":"face"}
     * engine_num : A6270231N20
     * issue_date : 20111
     * model : NWBA35110
     * owner : 卫超
     * plate_num : 陕A6Z171
     * register_date : 20120911
     * request_id : 20180816154525_b6f9a2e2925e5711b8764ed508d80b40
     * success : true
     * use_character : 非营运
     * vehicle_type : 小型轿车
     * vin : WBA1B1108CF379615
     */

    private String addr;
    private String config_str;
    private String engine_num;
    private String issue_date;
    private String model;
    private String owner;
    private String plate_num;
    private String register_date;
    private String request_id;
    private boolean success;
    private String use_character;
    private String vehicle_type;
    private String vin;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getConfig_str() {
        return config_str;
    }

    public void setConfig_str(String config_str) {
        this.config_str = config_str;
    }

    public String getEngine_num() {
        return engine_num;
    }

    public void setEngine_num(String engine_num) {
        this.engine_num = engine_num;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlate_num() {
        return plate_num;
    }

    public void setPlate_num(String plate_num) {
        this.plate_num = plate_num;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUse_character() {
        return use_character;
    }

    public void setUse_character(String use_character) {
        this.use_character = use_character;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}

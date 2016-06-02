package com.cx.measure.bean;

import java.util.List;

/**
 * Created by yyao on 2016/6/1.
 * 工位
 */
public class Workbench {

    private int id;
    private int pitId;

    private String name;
    private String RFID;
    /** 经度 */
    private double longitude;
    /** 纬度 */
    private double latitude;

    /** 包含的点位 */
    private List<WorkPoint> workPoints;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPitId() {
        return pitId;
    }

    public void setPitId(int pitId) {
        this.pitId = pitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<WorkPoint> getWorkPoints() {
        return workPoints;
    }

    public void setWorkPoints(List<WorkPoint> workPoints) {
        this.workPoints = workPoints;
    }
}

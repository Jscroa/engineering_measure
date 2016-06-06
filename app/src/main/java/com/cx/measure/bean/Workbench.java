package com.cx.measure.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

/**
 * Created by yyao on 2016/6/1.
 * 工位
 */
@Table(name = "t_workbench")
public class Workbench {

    /** 本地数据库id */
    @Column(name = "id",isId = true)
    private int id;

    /** 服务器表id */
    @Column(name = "server_id")
    private int serverId;

    /** 是否已经上传 */
    @Column(name = "has_upload")
    private boolean hasUpload;

    /** 主表id */
    @Column(name = "pit_id")
    private int pitId;

    /** 工位名 */
    @Column(name = "name")
    private String name;

    /** RFID */
    @Column(name = "rfid")
    private String RFID;

    /** 经度 */
    @Column(name = "longitude")
    private double longitude;
    /** 纬度 */
    @Column(name = "latitude")
    private double latitude;

    /** 创建时间 */
    @Column(name = "create_time")
    private long createTime;

    /** 更新时间 */
    @Column(name = "update_time")
    private long updateTime;

    /** 包含的点位 */
    private List<WorkPoint> workPoints;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public boolean isHasUpload() {
        return hasUpload;
    }

    public void setHasUpload(boolean hasUpload) {
        this.hasUpload = hasUpload;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public List<WorkPoint> getWorkPoints() {
        return workPoints;
    }

    public void setWorkPoints(List<WorkPoint> workPoints) {
        this.workPoints = workPoints;
    }
}

package com.cx.measure.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by yyao on 2016/6/7.
 */
@Table(name = "t_measure_data")
public class MeasureData implements Serializable {

    /** 本地数据库id */
    @Column(name = "id",isId = true)
    private int id;

    /** 唯一标识 */
    @Column(name = "uuid")
    private String uuid;

    /** 服务器表id */
    @Column(name = "server_id")
    private int serverId;

    /** 是否已经上传 */
    @Column(name = "has_upload")
    private boolean hasUpload;

    /** 点位ID */
    @Column(name = "point_id")
    private int pointId;

    /** 测量数据 */
    @Column(name = "data")
    private double data;

    /** 创建时间 */
    @Column(name = "create_time")
    private long createTime;

    /** 更新时间 */
    @Column(name = "update_time")
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
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
}

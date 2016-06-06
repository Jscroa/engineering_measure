package com.cx.measure.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by yyao on 2016/6/1.
 * 点位
 */
@Table(name = "t_work_point")
public class WorkPoint {

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
    @Column(name = "workbench_id")
    private int workbenchId;

    /** 点位名称 */
    @Column(name = "name")
    private String name;

    /** 测量方法 */
    @Column(name = "measure_type")
    private int measureType;

    /** 测量次数 */
    @Column(name = "measure_count")
    private int measureCount;

    /** 误差范围 默认±10% */
    @Column(name = "deviation_percent")
    private int deviationPercent = 10;

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

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public boolean isHasUpload() {
        return hasUpload;
    }

    public void setHasUpload(boolean hasUpload) {
        this.hasUpload = hasUpload;
    }

    public int getWorkbenchId() {
        return workbenchId;
    }

    public void setWorkbenchId(int workbenchId) {
        this.workbenchId = workbenchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMeasureType() {
        return measureType;
    }

    public void setMeasureType(int measureType) {
        this.measureType = measureType;
    }

    public int getMeasureCount() {
        return measureCount;
    }

    public void setMeasureCount(int measureCount) {
        this.measureCount = measureCount;
    }

    public int getDeviationPercent() {
        return deviationPercent;
    }

    public void setDeviationPercent(int deviationPercent) {
        this.deviationPercent = deviationPercent;
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

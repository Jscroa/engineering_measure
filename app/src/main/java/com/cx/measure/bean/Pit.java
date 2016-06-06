package com.cx.measure.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by yyao on 2016/6/1.
 * 基坑
 */
@Table(name = "t_pit")
public class Pit {

    /** 本地数据库id */
    @Column(name = "id",isId = true)
    private int id;

    /** 服务器表id */
    @Column(name = "server_id")
    private int serverId;

    /** 是否已经上传 */
    @Column(name = "has_upload")
    private boolean hasUpload;

    /** 工位名 */
    @Column(name = "name")
    private String name;

    /** 创建时间 */
    @Column(name = "create_time")
    private Timestamp createTime;

    /** 更新时间 */
    @Column(name = "update_time")
    private Timestamp updateTime;

    /** 包含的工位 */
    private List<Workbench> workbenches;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public List<Workbench> getWorkbenches() {
        return workbenches;
    }

    public void setWorkbenches(List<Workbench> workbenches) {
        this.workbenches = workbenches;
    }
}

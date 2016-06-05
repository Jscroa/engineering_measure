package com.cx.measure.bean;

import com.cx.measure.comments.MeasureType;

/**
 * Created by yyao on 2016/6/1.
 * 点位
 */
public class WorkPoint {

    private int id;
    private int workbenchId;

    /** 点位名称 */
    private String name;

    /** 测量方法 */
    private MeasureType type;
    /** 测量次数 */
    private int measureCount;
    /** 误差范围 默认±10% */
    private int deviationPercent = 10;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public MeasureType getType() {
        return type;
    }

    public void setType(MeasureType type) {
        this.type = type;
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
}

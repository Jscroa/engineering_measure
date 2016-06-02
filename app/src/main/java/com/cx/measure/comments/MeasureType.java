package com.cx.measure.comments;

/**
 * Created by yyao on 2016/5/31.
 */
public enum MeasureType {
    /** 水位 */
    WATER_LEVEL("水位",100),
    /** 测斜 */
    OBLIQUE("测斜",200),
    /** 轴力 */
    AXIAL_FORCE("轴力",300),
    /** 钢筋 */
    STELL_BAR("钢筋",400),
    /** 沉降 */
    SETTLEMENT("沉降",500);

    private String name;
    private int code;
    private MeasureType(String name, int code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

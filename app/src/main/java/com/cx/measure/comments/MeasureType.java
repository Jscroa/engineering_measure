package com.cx.measure.comments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public int getCode() {
        return code;
    }

    public static List<MeasureType> allTypes = Arrays.asList(MeasureType.values());

    public static MeasureType getType(int code){
        for (MeasureType type:allTypes) {
            if (type.getCode() == code){
                return type;
            }
        }
        return null;
    }

    public static int getPositioon(int code){
        for (int i = 0; i < allTypes.size(); i++) {
            MeasureType type = allTypes.get(i);
            if(type.getCode() == code){
                return i;
            }
        }
        return -1;
    }

    public static MeasureType getByPosition(int position){
        if(position >=0 || position<allTypes.size()){
            return allTypes.get(position);
        }
        return null;
    }

    public static List<String> getNameList(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < allTypes.size(); i++) {
            MeasureType type = allTypes.get(i);
            list.add(type.getName());
        }
        return list;
    }

}

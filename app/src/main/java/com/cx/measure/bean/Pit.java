package com.cx.measure.bean;

import java.util.List;

/**
 * Created by yyao on 2016/6/1.
 * 基坑
 */
public class Pit {

    private int id;

    private String name;

    /** 包含的工位 */
    private List<Workbench> workbenches;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Workbench> getWorkbenches() {
        return workbenches;
    }

    public void setWorkbenches(List<Workbench> workbenches) {
        this.workbenches = workbenches;
    }
}

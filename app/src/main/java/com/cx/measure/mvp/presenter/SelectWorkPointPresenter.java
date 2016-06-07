package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.WorkPoint;
import com.cx.measure.dao.WorkPointDao;
import com.cx.measure.mvp.view.SelectWorkPointView;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/7.
 */
public class SelectWorkPointPresenter {
    private SelectWorkPointView view;
    private int workPointId;

    private List<WorkPoint> workPoints;
    public SelectWorkPointPresenter(SelectWorkPointView view, int workPointId) {
        this.view = view;
        this.workPointId = workPointId;
        WorkPointDao dao = new WorkPointDao();
        try {
            workPoints = dao.getWorkPoints(workPointId);
        } catch (DbException e) {
            e.printStackTrace();
            workPoints = new ArrayList<>();
        }
    }

    public List<String> getWorkPointsNames(){
        List<String> names = new ArrayList<>();
        for (int i = 0; i < workPoints.size(); i++) {
            names.add("点位："+workPoints.get(i).getName());
        }
        return names;
    }

    public int getPointId(int position){
        return workPoints.get(position).getId();
    }
}

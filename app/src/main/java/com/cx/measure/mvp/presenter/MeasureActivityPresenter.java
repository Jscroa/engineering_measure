package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.MeasureData;
import com.cx.measure.bean.WorkPoint;
import com.cx.measure.dao.MeasureDataDao;
import com.cx.measure.dao.WorkPointDao;
import com.cx.measure.mvp.view.MeasureActivityView;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/5/31.
 */
public class MeasureActivityPresenter implements HomeAsUpEnabledPresenter {
    MeasureActivityView view;

    private int workPointId;

    private WorkPoint workPoint;

    public MeasureActivityPresenter(MeasureActivityView view, int workPointId) throws DbException {
        this.view = view;
        this.workPointId = workPointId;
        WorkPointDao dao = new WorkPointDao();
        workPoint = dao.getWorkPoint(workPointId);

    }

    public WorkPoint getWorkPoint() {
        return workPoint;
    }

    @Override
    public void back() {
        view.back();
    }

    public boolean save(List<Double> values) {
        cleanValues();
        long now = System.currentTimeMillis();
        MeasureDataDao dao = new MeasureDataDao();
        try {
            for (double value : values) {
                MeasureData data = new MeasureData();
                data.setPointId(workPointId);
                data.setData(value);
                data.setCreateTime(now);
                data.setUpdateTime(now);
                dao.add(data);
            }
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cleanValues(){
        MeasureDataDao dao = new MeasureDataDao();
        try {
            dao.deleteByPoint(workPointId);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void restore(){
        MeasureDataDao dao = new MeasureDataDao();
        List<Double> values = new ArrayList<>();
        try {
            List<MeasureData> datas = dao.getByPoint(workPointId);
            if(datas==null){
                return;
            }
            for (MeasureData data : datas) {
                values.add(data.getData());
            }
            view.setValues(values);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return;
    }
}

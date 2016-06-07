package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.WorkPoint;
import com.cx.measure.dao.WorkPointDao;
import com.cx.measure.mvp.view.MeasureActivityView;

import org.xutils.ex.DbException;

/**
 * Created by yyao on 2016/5/31.
 */
public class MeasureActivityPresenter implements HomeAsUpEnabledPresenter {
    MeasureActivityView view;

    private int workPointId;

    private WorkPoint workPoint;
    public MeasureActivityPresenter(MeasureActivityView view,int workPointId) throws DbException {
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
}

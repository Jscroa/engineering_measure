package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.Workbench;
import com.cx.measure.dao.WorkbenchDao;
import com.cx.measure.mvp.view.HomeAsUpEnabledView;
import com.cx.measure.mvp.view.MeasureByLocationActivityView;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/8.
 */
public class MeasureByLocationActivityPresenter implements HomeAsUpEnabledView {
    MeasureByLocationActivityView view;

    List<Workbench> workbenches = new ArrayList<>();

    public MeasureByLocationActivityPresenter(MeasureByLocationActivityView view) {
        this.view = view;
    }

    public List<String> getWorkbenchNames(double longitude,double latitude) throws DbException {

        WorkbenchDao dao = new WorkbenchDao();
        workbenches = dao.getNearWorkbenches(longitude,latitude);

        List<String> names = new ArrayList<>();
        if(workbenches==null){
            return names;
        }
        for (int i = 0; i < workbenches.size(); i++) {
            names.add("工位："+workbenches.get(i).getName());
        }
        return names;
    }
    public int getWorkbenchId(int position){
        return workbenches.get(position).getId();
    }
    @Override
    public void back() {
        view.back();
    }
}

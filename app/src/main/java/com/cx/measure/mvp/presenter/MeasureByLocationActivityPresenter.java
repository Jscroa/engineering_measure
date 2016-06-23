package com.cx.measure.mvp.presenter;

import android.content.Context;

import com.cx.measure.bean.Workbench;
import com.cx.measure.mvp.view.HomeAsUpEnabledView;
import com.cx.measure.mvp.view.MeasureByLocationActivityView;

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

    public List<String> getWorkbenchNames(Context context, double longitude, double latitude) {

        com.cx.measure.dao.mysql.WorkbenchDao workbenchDao = new com.cx.measure.dao.mysql.WorkbenchDao();
        try {
            workbenches = workbenchDao.getNearWorkbenches(context,longitude,latitude);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
//        WorkbenchDao dao = new WorkbenchDao();
//        workbenches = dao.getNearWorkbenches(longitude,latitude);

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

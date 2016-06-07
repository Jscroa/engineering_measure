package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.Pit;
import com.cx.measure.bean.Workbench;
import com.cx.measure.dao.PitDao;
import com.cx.measure.dao.WorkbenchDao;
import com.cx.measure.mvp.view.SelectWorkbenchView;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/7.
 */
public class SelectWorkbenchPresenter {
    SelectWorkbenchView view;

    List<Pit> pits = new ArrayList<>();
    List<Workbench> workbenches = new ArrayList<>();

    public SelectWorkbenchPresenter(SelectWorkbenchView view) {
        this.view = view;
        PitDao dao = new PitDao();
        try {
            pits = dao.getAll();
        } catch (DbException e) {
            e.printStackTrace();
            pits = new ArrayList<>();
        }
    }

    public List<Pit> getPits() {
        for (int i = 0; i < pits.size(); i++) {
            Pit pit = pits.get(i);
            pit.setWorkbenches(getWorkbenches(pit.getId()));
            pits.set(i,pit);
        }
        return pits;
    }

    public List<Workbench> getWorkbenches(int pitId) {
        WorkbenchDao dao = new WorkbenchDao();
        try {
            workbenches = dao.getWorkbenches(pitId);
        } catch (DbException e) {
            e.printStackTrace();
            workbenches = new ArrayList<>();
        }
        return workbenches;
    }

    public Pit getPit(int position) {
        return pits.get(position);
    }

    public int getWorkbenchId(int groupPosition,int childPosition){
        return pits.get(groupPosition).getWorkbenches().get(childPosition).getId();
    }

}

package com.cx.measure.mvp.presenter;

import android.content.Context;
import android.os.AsyncTask;

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
    Context context;
    private SelectWorkPointView view;
    private int workPointId;

    private List<WorkPoint> workPoints;

    public SelectWorkPointPresenter(Context context, SelectWorkPointView view, int workPointId) {
        this.context = context;
        this.view = view;
        this.workPointId = workPointId;
//        WorkPointDao dao = new WorkPointDao();

//        try {
//            com.cx.measure.dao.mysql.WorkPointDao workPointDao = new com.cx.measure.dao.mysql.WorkPointDao();
//            workPoints = workPointDao.getWorkPoints(context, workPointId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            workPoints = new ArrayList<>();
//        }
    }

    public void reqPoints() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    com.cx.measure.dao.mysql.WorkPointDao workPointDao = new com.cx.measure.dao.mysql.WorkPointDao();
                    workPoints = workPointDao.getWorkPoints(context, workPointId);
                } catch (Exception e) {
                    e.printStackTrace();
                    workPoints = new ArrayList<>();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                view.refresh();
            }
        }.execute();

    }

    public List<String> getWorkPointsNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < workPoints.size(); i++) {
            names.add("点位：" + workPoints.get(i).getName());
        }
        return names;
    }

    public int getPointId(int position) {
        return workPoints.get(position).getId();
    }
}

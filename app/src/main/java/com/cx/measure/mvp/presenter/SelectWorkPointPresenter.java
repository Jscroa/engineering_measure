package com.cx.measure.mvp.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.cx.measure.bean.WorkPoint;
import com.cx.measure.bean.Workbench;
import com.cx.measure.dao.WorkPointDao;
import com.cx.measure.dao.mysql.WorkbenchDao;
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
    private int workbenchId;
    // 是否是扫描rfid进入
    private boolean isRfid;
    private String rfid;

    private List<WorkPoint> workPoints;

    public SelectWorkPointPresenter(Context context, SelectWorkPointView view, int workbenchId) {
        this.context = context;
        this.view = view;
        this.workbenchId = workbenchId;
//        WorkPointDao dao = new WorkPointDao();
    }

    public SelectWorkPointPresenter(Context context, SelectWorkPointView view, String rfid) {
        this.context = context;
        this.view = view;
        this.isRfid = true;
        this.rfid = rfid;

    }

    public void reqPoints() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    if (isRfid) {
                        com.cx.measure.dao.mysql.WorkbenchDao workbenchDao = new com.cx.measure.dao.mysql.WorkbenchDao();
                        Workbench workbench = workbenchDao.getByRfid(context, rfid);
                        if (workbench != null) {
                            workbenchId = workbench.getId();
                        }
                    }
                    com.cx.measure.dao.mysql.WorkPointDao workPointDao = new com.cx.measure.dao.mysql.WorkPointDao();
                    workPoints = workPointDao.getWorkPoints(context, workbenchId);
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

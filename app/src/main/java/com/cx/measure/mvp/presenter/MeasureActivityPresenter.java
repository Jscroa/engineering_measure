package com.cx.measure.mvp.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.cx.measure.bean.MeasureData;
import com.cx.measure.bean.WorkPoint;
import com.cx.measure.mvp.view.MeasureActivityView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/5/31.
 */
public class MeasureActivityPresenter implements HomeAsUpEnabledPresenter {
    Context context;
    MeasureActivityView view;

    private int workPointId;

    private WorkPoint workPoint;
    List<Double> values;
    public MeasureActivityPresenter(Context context, MeasureActivityView view, int workPointId) {
        this.context = context;
        this.view = view;
        this.workPointId = workPointId;

//        WorkPointDao dao = new WorkPointDao();
//        workPoint = dao.getWorkPoint(workPointId);

    }

    public void reqPoint() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    com.cx.measure.dao.mysql.WorkPointDao workPointDao = new com.cx.measure.dao.mysql.WorkPointDao();
                    workPoint = workPointDao.getWorkPoint(context, workPointId);
                } catch (Exception e) {
                    e.printStackTrace();
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

    public WorkPoint getWorkPoint() {
        return workPoint;
    }

    @Override
    public void back() {
        view.back();
    }

    public boolean save(final List<Double> values) {

        try {
            com.cx.measure.dao.mysql.MeasureDataDao measureDataDao = new com.cx.measure.dao.mysql.MeasureDataDao();
//            measureDataDao.deleteByPoint(context, workPointId);
            long now = System.currentTimeMillis();
            List<MeasureData> measureDatas = new ArrayList<>();
            for (double value : values) {
                MeasureData data = new MeasureData();
                data.setPointId(workPointId);
                data.setData(value);
                data.setCreateTime(now);
                data.setUpdateTime(now);
                measureDatas.add(data);
            }
            measureDataDao.add(context, measureDatas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public void cleanValues() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    com.cx.measure.dao.mysql.MeasureDataDao measureDataDao = new com.cx.measure.dao.mysql.MeasureDataDao();
                    measureDataDao.deleteByPoint(context, workPointId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

//    public void restore() {
//        new AsyncTask<Void,Void,Void>(){
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try{
//                    com.cx.measure.dao.mysql.MeasureDataDao measureDataDao = new com.cx.measure.dao.mysql.MeasureDataDao();
//                    values = new ArrayList<>();
//                    List<MeasureData> datas = measureDataDao.getByPoint(context,workPointId);
//                    if (datas == null) {
//                        return null;
//                    }
//                    for (MeasureData data : datas) {
//                        values.add(data.getData());
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                view.setValues(values);
//            }
//        }.execute();
//
//    }
}

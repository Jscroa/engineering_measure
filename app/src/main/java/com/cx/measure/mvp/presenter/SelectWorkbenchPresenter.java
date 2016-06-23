package com.cx.measure.mvp.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.cx.measure.bean.Pit;
import com.cx.measure.mvp.view.SelectWorkbenchView;
import com.cx.measure.view.MyProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/7.
 */
public class SelectWorkbenchPresenter {
    Context context;
    SelectWorkbenchView view;

    List<Pit> pits = new ArrayList<>();

    public SelectWorkbenchPresenter(Context context, SelectWorkbenchView view) {
        this.context = context;
        this.view = view;
//        PitDao dao = new PitDao();


    }

    public void reqPits(final MyProgressDialog myProgressDialog){
        myProgressDialog.setMessage("正在加载");
        myProgressDialog.show();
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    com.cx.measure.dao.mysql.PitDao pitDao = new com.cx.measure.dao.mysql.PitDao();
                    pits = pitDao.getAll(context);
                    if(pits==null){
                        pits = new ArrayList<>();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    pits = new ArrayList<>();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                myProgressDialog.dismiss();
                view.refresh();
            }
        }.execute();
    }

    public List<Pit> getPits() {

        return pits;
    }

//    public List<Workbench> getWorkbenches(int pitId) {
//        com.cx.measure.dao.mysql.WorkbenchDao workbenchDao = new com.cx.measure.dao.mysql.WorkbenchDao();
//        List<Workbench> workbenches;
//        try {
//            workbenches = workbenchDao.getWorkbenches(context,pitId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            workbenches = new ArrayList<>();
//        }
//        return workbenches;
//    }

    public Pit getPit(int position) {
        return pits.get(position);
    }

    public int getWorkbenchId(int groupPosition,int childPosition){
        return pits.get(groupPosition).getWorkbenches().get(childPosition).getId();
    }

}

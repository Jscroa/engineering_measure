package com.cx.measure.task;

import android.content.Context;
import android.os.AsyncTask;

import com.cx.measure.bean.Pit;
import com.cx.measure.bean.WorkPoint;
import com.cx.measure.bean.Workbench;
import com.cx.measure.dao.PitDao;
import com.cx.measure.dao.WorkPointDao;
import com.cx.measure.dao.WorkbenchDao;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传基坑设置
 * Created by yyao on 2016/6/16.
 */
public class UploadPitSettingTask extends AsyncTask<Void, String, String> {

    private Context context;
    private IProgressView iProgressView;
    private IFinishedCallback finishedCallback;

    public UploadPitSettingTask(Context context,IProgressView iProgressView,IFinishedCallback finishedCallback) {
        this.context = context;
        this.iProgressView = iProgressView;
        this.finishedCallback = finishedCallback;
    }

    @Override
    protected String doInBackground(Void... params) {
        int success = 0;
        int failure = 0;
        try {
            PitDao pitDao = new PitDao();
            // 获取未上传的基坑
            List<Pit> unuploadPits = pitDao.getUnupload();
            if(unuploadPits==null || unuploadPits.size() <=0){
                return "没有需要上传的设置";
            }
            WorkbenchDao workbenchDao = new WorkbenchDao();
            WorkPointDao workPointDao = new WorkPointDao();
            int totalsize = unuploadPits.size();
            for (int i = 0; i < totalsize ; i++) {

                String progress1 = String.format("(%d/%d)-1 正在上传",i+1,totalsize);
                publishProgress(progress1);

                Pit pit = unuploadPits.get(i);
                // 拉取pit详细
                List<Workbench> workbenches = workbenchDao.getWorkbenches(pit.getId());
                if(workbenches==null || workbenches.size()<=0){
                    pit.setWorkbenches(new ArrayList<Workbench>());
                }else{
                    for (int j = 0; j < workbenches.size(); j++) {
                        Workbench workbench = workbenches.get(j);
                        List<WorkPoint> workPoints = workPointDao.getWorkPoints(workbench.getId());
                        if(workPoints==null || workPoints.size()<=0){
                            workbench.setWorkPoints(new ArrayList<WorkPoint>());
                        }else{
                            workbench.setWorkPoints(workPoints);
                        }
                        workbenches.set(j,workbench);
                    }
                    pit.setWorkbenches(workbenches);
                }

                String progress2 = String.format("(%d/%d)-2 正在上传",i+1,totalsize);
                publishProgress(progress2);

                // 上传
                com.cx.measure.dao.mysql.PitDao mysqlPitDao = new com.cx.measure.dao.mysql.PitDao();
                try {
                    mysqlPitDao.add(context,pit);
                    success++;
                } catch (Exception e) {
                    e.printStackTrace();
                    failure++;
                }

                // 修改本地
                String progress3 = String.format("(%d/%d)-3 正在上传",i+1,totalsize);
                publishProgress(progress2);

                pitDao.updateSuccess(pit.getId());

            }
        } catch (DbException e) {
            e.printStackTrace();
            return "上传失败";
        }
        return "上传完成：成功"+success+",失败"+failure;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if(iProgressView!=null){
            iProgressView.setProgress(values[0]);
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(finishedCallback!=null){
            finishedCallback.finish(s);
        }

    }

    public interface IFinishedCallback{
        void finish(String s);
    }

    public interface IProgressView{
        void show();
        void setProgress(String progress);
        void dismiss();
    }

}

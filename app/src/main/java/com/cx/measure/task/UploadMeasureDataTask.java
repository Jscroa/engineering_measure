package com.cx.measure.task;

import android.content.Context;
import android.os.AsyncTask;

import com.cx.measure.bean.MeasureData;
import com.cx.measure.dao.MeasureDataDao;

import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by yyao on 2016/6/18.
 */
public class UploadMeasureDataTask extends AsyncTask<Void, String, String> {
    private Context context;
    private IProgressView iProgressView;
    private IFinishedCallback finishedCallback;

    public UploadMeasureDataTask(Context context, IProgressView iProgressView, IFinishedCallback finishedCallback) {
        this.context = context;
        this.iProgressView = iProgressView;
        this.finishedCallback = finishedCallback;
    }

    @Override
    protected String doInBackground(Void... params) {
        int success = 0;
        int failure = 0;
        try {
            MeasureDataDao measureDataDao = new MeasureDataDao();
            List<MeasureData> measureDatas = measureDataDao.getUnupload();
            if(measureDatas==null || measureDatas.size() <=0){
                return "没有需要上传的设置";
            }
            int totalsize = measureDatas.size();

            try {
                com.cx.measure.dao.mysql.MeasureDataDao mysqlMeasureDataDao = new com.cx.measure.dao.mysql.MeasureDataDao();
                mysqlMeasureDataDao.add(context,measureDatas);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (DbException e) {
            e.printStackTrace();
            return "上传失败";
        }
        return "上传完成：成功"+success+",失败"+failure;

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

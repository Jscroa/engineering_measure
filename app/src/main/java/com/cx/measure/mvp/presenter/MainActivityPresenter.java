package com.cx.measure.mvp.presenter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.cx.measure.dao.PitDao;
import com.cx.measure.mvp.view.MainActivityView;
import com.cx.measure.task.UploadPitSettingTask;

import org.xutils.ex.DbException;

/**
 * Created by yyao on 2016/5/31.
 */
public class MainActivityPresenter {
    MainActivityView view;

    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
    }

    public void clickToInit() {
        view.toInit();
    }

    public void clickToMeasure() {
        view.toMeasure();
    }
    public void clickToLocation() {
        view.toLocation();
    }

    public void clickToSelect(){
        view.toSelect();
    }

    public SpannableString getComment(final Context context, final UploadPitSettingTask.IProgressView iProgressView){
        PitDao pitDao = new PitDao();
        try {
            int count = pitDao.getUnuploadCount();
            SpannableString str =  new SpannableString("本地有"+count+"个初始化设置未上传，点我上传");
            int end = str.length();
            int start = end-4;
            str.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Toast.makeText(context,"aasdas",Toast.LENGTH_SHORT).show();
                    new UploadPitSettingTask(context, iProgressView).execute();
                    iProgressView.show();
                }
            },start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return str;
        } catch (DbException e) {
            e.printStackTrace();
            return new SpannableString(e.getMessage());
        }
    }
}

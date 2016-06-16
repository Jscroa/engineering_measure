package com.cx.measure.view;

import android.app.ProgressDialog;
import android.content.Context;

import com.cx.measure.task.UploadPitSettingTask;

/**
 * Created by yyao on 2016/6/16.
 */
public class MyProgressDialog extends ProgressDialog implements UploadPitSettingTask.IProgressView {

    public MyProgressDialog(Context context) {
        super(context);
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void setProgress(String progress) {
        setTitle(progress);
        setMessage(progress);
    }
}

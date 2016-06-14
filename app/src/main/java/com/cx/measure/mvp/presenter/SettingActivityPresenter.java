package com.cx.measure.mvp.presenter;

import android.content.Context;

import com.cx.measure.comments.SharedPreferencesUtil;
import com.cx.measure.mvp.view.SettingActivityView;

/**
 * Created by yyao on 2016/6/14.
 */
public class SettingActivityPresenter implements HomeAsUpEnabledPresenter {

    SettingActivityView view;

    public SettingActivityPresenter(SettingActivityView view) {
        this.view = view;
    }

    @Override
    public void back() {
        view.back();
    }

    public void restoreHost(Context context){
        String host = SharedPreferencesUtil.getHost(context);
        view.setHost(host);
    }

    public void saveHost(Context context,String host){
        SharedPreferencesUtil.setHost(context,host);
        view.back();
    }
}

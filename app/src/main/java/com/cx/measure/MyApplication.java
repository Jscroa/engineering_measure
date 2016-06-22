package com.cx.measure;

import android.app.Application;

import com.cx.measure.comments.LocationTask;

import org.xutils.x;

/**
 * Created by yyao on 2016/6/6.
 */
public class MyApplication extends Application {

    public LocationTask locationTask;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationTask = new LocationTask(getApplicationContext());
        x.Ext.init(this);
    }
}

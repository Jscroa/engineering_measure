package com.cx.measure;

import android.app.Application;

import org.xutils.x;

/**
 * Created by yyao on 2016/6/6.
 */
public class MyApplication extends Application {
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
        x.Ext.init(this);
    }
}

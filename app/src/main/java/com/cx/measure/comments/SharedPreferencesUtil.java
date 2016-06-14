package com.cx.measure.comments;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yyao on 2016/6/14.
 */
public class SharedPreferencesUtil {

    private static SharedPreferences init(Context context) {
        return context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public static String getHost(Context context) {
        SharedPreferences sp = init(context);
        return sp.getString("host", "");
    }

    public static void setHost(Context context, String host) {
        SharedPreferences sp = init(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("host", host);
        editor.commit();
    }

}

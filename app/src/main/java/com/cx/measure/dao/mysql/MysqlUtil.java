package com.cx.measure.dao.mysql;

import android.content.Context;
import android.util.Log;

import com.cx.measure.comments.SharedPreferencesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by yyao on 2016/6/14.
 */
public class MysqlUtil {

    public static Connection getConnection(Context context) throws Exception {
        Connection con = null;
        try{
            String host = SharedPreferencesUtil.getHost(context);
            String url = "jdbc:mysql://"+host+":3306/measure?useUnicode=true&characterEncoding=UTF-8";
            String userName = "measure";
            String password = "123456";

            Log.i("TAG","------------- "+url);
            con = DriverManager.getConnection(url,userName,password);
            if(con == null){
                throw new Exception("未能获取数据库连接");
            }
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("未能加载驱动",e);
        }
    }

    public static void close(Connection con, Statement stat, ResultSet rs){
        close(rs);
        close(stat);
        close(con);
    }
    public static void close(Connection con, Statement stat){
        close(stat);
        close(con);
    }

    public static void close(Connection con) {
        if(con!=null){
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement stat) {
        if(stat!=null){
            try {
                stat.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if(rs!=null){
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

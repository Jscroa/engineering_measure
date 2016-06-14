package com.cx.measure.dao.mysql;

import android.content.Context;

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
            String url = "jdbc:mysql://"+host+":3306/measure";
            String userName = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,userName,password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("未能加载驱动",e);
        }
        return con;
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

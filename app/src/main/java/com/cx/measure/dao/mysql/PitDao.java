package com.cx.measure.dao.mysql;

import android.content.Context;

import com.cx.measure.bean.Pit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yyao on 2016/6/14.
 */
public class PitDao {

    public void add(Context context, Pit pit) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = MysqlUtil.getConnection(context);
            String sql = "";
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据库错误", e);
        }
    }
}

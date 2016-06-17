package com.cx.measure.dao.mysql;

import android.content.Context;

import com.cx.measure.bean.MeasureData;
import com.cx.measure.comments.UUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by yyao on 2016/6/14.
 */
public class MeasureDataDao {

    /**
     * 添加测量数据，带事务
     *
     * @param context
     * @param measureDatas
     * @throws Exception
     */
    public void add(Context context, List<MeasureData> measureDatas) throws Exception {
        Connection conn = null;
        try {
            conn = MysqlUtil.getConnection(context);
            conn.setAutoCommit(false);
            for (MeasureData measureData : measureDatas) {
                add(conn, measureData);
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }

            e.printStackTrace();
            throw new Exception("数据库错误", e);
        } finally {
            if (conn != null) {
                MysqlUtil.close(conn);
            }
        }
    }

    public void add(Connection conn, MeasureData measureData) throws Exception {
        long now = System.currentTimeMillis();

        if (measureData.getUuid() == null || "".equals(measureData.getUuid())) {
            measureData.setUuid(UUIDUtil.createUUID());
        }
        if (measureData.getCreateTime() == 0l) {
            measureData.setCreateTime(now);
        }
        measureData.setUpdateTime(now);

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String sql = "insert into t_measure_data(uuid,point_id,data,create_time,update_time) values(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, measureData.getUuid());
            pst.setInt(2, measureData.getPointId());
            pst.setDouble(3, measureData.getData());
            pst.setLong(4, measureData.getCreateTime());
            pst.setLong(5, measureData.getUpdateTime());
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据库错误", e);
        } finally {
            MysqlUtil.close(rs);
            MysqlUtil.close(pst);
        }
    }
}

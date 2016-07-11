package com.cx.measure.dao.mysql;

import android.content.Context;
import android.widget.Toast;

import com.cx.measure.bean.MeasureData;
import com.cx.measure.comments.UUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
            Toast.makeText(context,"数据库发生错误，"+e.getMessage(),Toast.LENGTH_SHORT).show();
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
            String sql = "insert into t_measure_data(uuid,point_id,data,create_time,update_time) values(?,?,?,UNIX_TIMESTAMP(NOW()) * 1000,UNIX_TIMESTAMP(NOW()) * 1000)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, measureData.getUuid());
            pst.setInt(2, measureData.getPointId());
            pst.setDouble(3, measureData.getData());
//            pst.setLong(4, measureData.getCreateTime());
//            pst.setLong(5, measureData.getUpdateTime());
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

    public void deleteByPoint(Context context, int pointId) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        try{
            conn = MysqlUtil.getConnection(context);
            String sql = "delete from t_measure_data where point_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,pointId);
            pst.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"数据库发生错误，"+e.getMessage(),Toast.LENGTH_SHORT).show();
            throw new Exception("数据库错误", e);
        } finally {
            MysqlUtil.close(conn,pst);
        }
    }

    public List<MeasureData> getByPoint(Context context,int pointId) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<MeasureData> measureDatas = new ArrayList<>();
        try {
            conn = MysqlUtil.getConnection(context);
            String sql = "select id,point_id,data,create_time,update_time from t_measure_data where point_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,pointId);
            rs = pst.executeQuery();
            if(rs == null){
                return measureDatas;
            }

            while (rs.next()){
                MeasureData measureData = new MeasureData();
                measureData.setId(rs.getInt("id"));
                measureData.setPointId(rs.getInt("point_id"));
                measureData.setData(rs.getDouble("data"));
                measureData.setCreateTime(rs.getLong("create_time"));
                measureData.setUpdateTime(rs.getLong("update_time"));
                measureDatas.add(measureData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"数据库发生错误，"+e.getMessage(),Toast.LENGTH_SHORT).show();
            throw new Exception("数据库错误",e);
        }finally {
            MysqlUtil.close(conn,pst,rs);
        }
        return measureDatas;
    }
}

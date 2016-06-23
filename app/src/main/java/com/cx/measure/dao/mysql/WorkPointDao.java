package com.cx.measure.dao.mysql;

import android.content.Context;
import android.widget.Toast;

import com.cx.measure.bean.WorkPoint;
import com.cx.measure.comments.UUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/16.
 */
public class WorkPointDao {

    public void add(Connection conn, WorkPoint workPoint) throws Exception {
        long now = System.currentTimeMillis();

        if(workPoint.getUuid() ==null || "".equals(workPoint.getUuid())){
            workPoint.setUuid(UUIDUtil.createUUID());
        }
        if(workPoint.getCreateTime() == 0l){
            workPoint.setCreateTime(now);
        }
        workPoint.setUpdateTime(now);

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String sql = "insert into t_work_point(uuid,workbench_id,name,measure_type,measure_count,deviation_percent,create_time,update_time) values(?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,workPoint.getUuid());
            pst.setInt(2,workPoint.getWorkbenchId());
            pst.setString(3,workPoint.getName());
            pst.setInt(4,workPoint.getMeasureType());
            pst.setInt(5,workPoint.getMeasureCount());
            pst.setInt(6,workPoint.getDeviationPercent());
            pst.setLong(7,workPoint.getCreateTime());
            pst.setLong(8,workPoint.getUpdateTime());
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据库错误", e);
        }finally {
            MysqlUtil.close(rs);
            MysqlUtil.close(pst);
        }
    }

    public List<WorkPoint> getWorkPoints(Context context,int workbenchId) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<WorkPoint> workPoints = new ArrayList<>();
        try {
            conn = MysqlUtil.getConnection(context);
            String sql = "select id,uuid,workbench_id,name,measure_type,measure_count,deviation_percent,create_time,update_time from t_work_point where workbench_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,workbenchId);
            rs = pst.executeQuery();
            if(rs == null){
                return workPoints;
            }

            while (rs.next()){
                WorkPoint workPoint = new WorkPoint();
                workPoint.setId(rs.getInt("id"));
                workPoint.setUuid(rs.getString("uuid"));
                workPoint.setWorkbenchId(rs.getInt("workbench_id"));
                workPoint.setName(rs.getString("name"));
                workPoint.setMeasureType(rs.getInt("measure_type"));
                workPoint.setMeasureCount(rs.getInt("measure_count"));
                workPoint.setDeviationPercent(rs.getInt("deviation_percent"));
                workPoint.setCreateTime(rs.getLong("create_time"));
                workPoint.setUpdateTime(rs.getLong("update_time"));
                workPoints.add(workPoint);
            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"数据库发生错误，"+e.getMessage(),Toast.LENGTH_SHORT).show();
            throw new Exception("数据库错误",e);
        }finally {
            MysqlUtil.close(conn,pst,rs);
        }
        return workPoints;
    }

    public WorkPoint getWorkPoint(Context context,int workPointId) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = MysqlUtil.getConnection(context);
            String sql = "select id,uuid,workbench_id,name,measure_type,measure_count,deviation_percent,create_time,update_time from t_work_point where id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,workPointId);
            rs = pst.executeQuery();
            if(rs == null){
                return null;
            }

            if (rs.next()){
                WorkPoint workPoint = new WorkPoint();
                workPoint.setId(rs.getInt("id"));
                workPoint.setUuid(rs.getString("uuid"));
                workPoint.setWorkbenchId(rs.getInt("workbench_id"));
                workPoint.setName(rs.getString("name"));
                workPoint.setMeasureType(rs.getInt("measure_type"));
                workPoint.setMeasureCount(rs.getInt("measure_count"));
                workPoint.setDeviationPercent(rs.getInt("deviation_percent"));
                workPoint.setCreateTime(rs.getLong("create_time"));
                workPoint.setUpdateTime(rs.getLong("update_time"));
                return workPoint;
            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"数据库发生错误，"+e.getMessage(),Toast.LENGTH_SHORT).show();
            throw new Exception("数据库错误",e);
        }finally {
            MysqlUtil.close(conn,pst,rs);
        }
        return null;
    }
}

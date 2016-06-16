package com.cx.measure.dao.mysql;

import android.content.Context;

import com.cx.measure.bean.WorkPoint;
import com.cx.measure.bean.Workbench;
import com.cx.measure.comments.UUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/16.
 */
public class WorkbenchDao {

    public void add(Connection conn, Workbench workbench) throws Exception {
        long now = System.currentTimeMillis();

        if(workbench.getUuid() == null || "".equals(workbench.getUuid())){
            workbench.setUuid(UUIDUtil.createUUID());
        }
        if(workbench.getCreateTime() == 0l){
            workbench.setCreateTime(now);
        }
        workbench.setUpdateTime(now);

        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            String sql = "insert into t_workbench(uuid,pit_id,name,rfid,longitude,latitude,create_time,update_time) values(?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,workbench.getUuid());
            pst.setInt(2,workbench.getPitId());
            pst.setString(3,workbench.getName());
            pst.setString(4,workbench.getRFID());
            pst.setDouble(5,workbench.getLongitude());
            pst.setDouble(6,workbench.getLatitude());
            pst.setLong(7,workbench.getCreateTime());
            pst.setLong(8,workbench.getUpdateTime());
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            if(workbench.getWorkPoints() ==null){
                return;
            }
            WorkPointDao workPointDao = new WorkPointDao();
            for(WorkPoint workPoint:workbench.getWorkPoints()){
                workPoint.setWorkbenchId(id);
                workPointDao.add(conn,workPoint);
            }

        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据库错误", e);
        }finally {
            MysqlUtil.close(rs);
            MysqlUtil.close(pst);
        }
    }

    public List<Workbench> getWorkbenches(Context context, int pitId) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Workbench> workbenches = new ArrayList<>();
        try {
            conn = MysqlUtil.getConnection(context);
            String sql = "select id,uuid,pit_id,name,rfid,longitude,latitude,create_time,update_time from t_workbench where pit_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,pitId);
            rs = pst.executeQuery();
            if(rs == null){
                return workbenches;
            }

            while (rs.next()){
                Workbench workbench = new Workbench();
                workbench.setId(rs.getInt("id"));
                workbench.setUuid(rs.getString("uuid"));
                workbench.setPitId(rs.getInt("pit_id"));
                workbench.setName(rs.getString("name"));
                workbench.setRFID(rs.getString("rfid"));
                workbench.setLongitude(rs.getDouble("longitude"));
                workbench.setLatitude(rs.getDouble("latitude"));
                workbench.setCreateTime(rs.getLong("create_time"));
                workbench.setUpdateTime(rs.getLong("update_time"));
                workbenches.add(workbench);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据库错误",e);
        }finally {
            MysqlUtil.close(conn,pst,rs);
        }
        return workbenches;
    }
}

package com.cx.measure.dao.mysql;

import android.content.Context;

import com.cx.measure.bean.Pit;
import com.cx.measure.bean.Workbench;
import com.cx.measure.comments.UUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/14.
 */
public class PitDao {

    /**
     * 添加基坑初始化项，带子表，带事务
     *
     * @param context
     * @param pit
     * @throws Exception
     */
    public void add(Context context, Pit pit) throws Exception {
        Connection conn = null;
        try {
            conn = MysqlUtil.getConnection(context);
            conn.setAutoCommit(false);
            add(conn, pit);
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

    public void add(Connection conn, Pit pit) throws Exception {
        long now = System.currentTimeMillis();

        if (pit.getUuid() == null || "".equals(pit.getUuid())) {
            pit.setUuid(UUIDUtil.createUUID());
        }
        if (pit.getCreateTime() == 0l) {
            pit.setCreateTime(now);
        }
        pit.setUpdateTime(now);

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String sql = "insert into t_pit(uuid,name,create_time,update_time) values(?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, pit.getUuid());
            pst.setString(2, pit.getName());
            pst.setLong(3, pit.getCreateTime());
            pst.setLong(4, pit.getUpdateTime());
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            if (pit.getWorkbenches() == null) {
                return;
            }
            WorkbenchDao workbenchDao = new WorkbenchDao();
            for (Workbench workbench : pit.getWorkbenches()) {
                workbench.setPitId(id);
                workbenchDao.add(conn, workbench);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据库错误", e);
        } finally {
            MysqlUtil.close(rs);
            MysqlUtil.close(pst);
        }
    }

    public List<Pit> getAll(Context context) throws Exception {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Pit> pits = new ArrayList<>();
        try {
            conn = MysqlUtil.getConnection(context);
            String sql = "select id,uuid,name,create_time,update_time from t_pit";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs == null) {
                return pits;
            }
            WorkbenchDao workbenchDao = new WorkbenchDao();
            while (rs.next()) {
                Pit pit = new Pit();
                pit.setId(rs.getInt("id"));
                pit.setUuid(rs.getString("uuid"));
                pit.setName(rs.getString("name"));
                pit.setCreateTime(rs.getLong("create_time"));
                pit.setUpdateTime(rs.getLong("update_time"));
                pit.setWorkbenches(workbenchDao.getWorkbenches(context,pit.getId()));
                pits.add(pit);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据库错误", e);
        } finally {
            MysqlUtil.close(conn, pst, rs);
        }
        return pits;
    }
}

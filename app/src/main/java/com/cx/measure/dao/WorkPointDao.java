package com.cx.measure.dao;

import com.cx.measure.bean.WorkPoint;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by yyao on 2016/6/6.
 */
public class WorkPointDao extends BaseDao {

    public void add(WorkPoint workPoint) throws DbException {
        long now = System.currentTimeMillis();
        DbManager db = x.getDb(daoConfig);
        workPoint.setCreateTime(now);
        workPoint.setUpdateTime(now);
        db.save(workPoint);
    }

    public List<WorkPoint> getWorkPoints(int workbenchId) throws DbException {
        DbManager db = x.getDb(daoConfig);
        List<WorkPoint> workPoints = db.selector(WorkPoint.class).where("workbench_id","=",workbenchId).findAll();
        return workPoints;
    }

    public WorkPoint getWorkPoint(int workPointId) throws DbException {
        DbManager db = x.getDb(daoConfig);
        WorkPoint workPoint = db.selector(WorkPoint.class).where("id","=",workPointId).findFirst();
        return workPoint;
    }
}

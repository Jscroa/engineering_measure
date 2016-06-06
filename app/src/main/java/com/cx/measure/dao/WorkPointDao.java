package com.cx.measure.dao;

import com.cx.measure.bean.WorkPoint;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.sql.Timestamp;

/**
 * Created by yyao on 2016/6/6.
 */
public class WorkPointDao extends BaseDao {

    public void add(WorkPoint workPoint) throws DbException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        DbManager db = x.getDb(daoConfig);
        workPoint.setCreateTime(now);
        workPoint.setUpdateTime(now);
        db.save(workPoint);
    }
}

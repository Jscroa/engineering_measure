package com.cx.measure.dao;

import com.cx.measure.bean.WorkPoint;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

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
}

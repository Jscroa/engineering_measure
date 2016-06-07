package com.cx.measure.dao;

import com.cx.measure.bean.MeasureData;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by yyao on 2016/6/7.
 */
public class MeasureDataDao extends BaseDao {

    public void add(MeasureData measureData) throws DbException {
        long now = System.currentTimeMillis();
        DbManager db = x.getDb(daoConfig);
        measureData.setCreateTime(now);
        measureData.setUpdateTime(now);
        db.save(measureData);
    }

}

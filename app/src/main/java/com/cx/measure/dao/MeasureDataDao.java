package com.cx.measure.dao;

import com.cx.measure.bean.MeasureData;
import com.cx.measure.comments.UUIDUtil;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by yyao on 2016/6/7.
 */
public class MeasureDataDao extends BaseDao {

    public void add(MeasureData measureData) throws DbException {
        long now = System.currentTimeMillis();
        DbManager db = x.getDb(daoConfig);
        measureData.setUuid(UUIDUtil.createUUID());
        measureData.setCreateTime(now);
        measureData.setUpdateTime(now);
        db.save(measureData);
    }

    public void deleteByPoint(int pointId) throws DbException {
        DbManager db = x.getDb(daoConfig);
        WhereBuilder where = WhereBuilder.b("point_id","=",pointId);
        db.delete(MeasureData.class,where);
    }

    public List<MeasureData> getByPoint(int pointId) throws DbException {
        DbManager db = x.getDb(daoConfig);
        return db.selector(MeasureData.class).where("point_id","=",pointId).findAll();
    }

    public List<MeasureData> getUnupload() throws DbException {
        DbManager db = x.getDb(daoConfig);
        List<MeasureData> measureDatas = db.selector(MeasureData.class).where("has_upload","=",false).findAll();
        return measureDatas;
    }

    public void updateSuccess(int id) throws DbException {
        DbManager db = x.getDb(daoConfig);
        WhereBuilder whereBuilder = WhereBuilder.b("id","=",id);
        KeyValue keyValue = new KeyValue("has_upload",true);
        db.update(MeasureData.class,whereBuilder,keyValue);
    }
}

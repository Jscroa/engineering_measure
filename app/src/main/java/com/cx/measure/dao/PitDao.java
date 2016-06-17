package com.cx.measure.dao;

import com.cx.measure.bean.Pit;
import com.cx.measure.bean.Workbench;
import com.cx.measure.comments.UUIDUtil;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by yyao on 2016/6/6.
 */
public class PitDao extends BaseDao {

    public void add(Pit pit) throws DbException {
        long now = System.currentTimeMillis();
        DbManager db = x.getDb(daoConfig);
        pit.setUuid(UUIDUtil.createUUID());
        pit.setCreateTime(now);
        pit.setUpdateTime(now);
        db.saveBindingId(pit);

        if(pit.getWorkbenches()==null){
            return;
        }
        WorkbenchDao workbenchDao = new WorkbenchDao();
        for (Workbench workbench:pit.getWorkbenches()) {
            workbench.setPitId(pit.getId());
            workbenchDao.add(workbench);
        }
    }

    public int getAllCount() throws DbException {
        DbManager db = x.getDb(daoConfig);
        long count = db.selector(Pit.class).count();
        return (int) count;
    }

    public int getUploadedCount() throws DbException {
        DbManager db = x.getDb(daoConfig);
        long count = db.selector(Pit.class).where("has_upload","=",true).count();
        return (int) count;
    }

    public int getUnuploadCount() throws DbException {
        DbManager db = x.getDb(daoConfig);
        long count = db.selector(Pit.class).where("has_upload","=",false).count();
        return (int) count;
    }

    public List<Pit> getAll() throws DbException {
        DbManager db = x.getDb(daoConfig);
        List<Pit> pits = db.selector(Pit.class).findAll();
        return pits;
    }

    /**
     * 获取未上传的pit
     * @return
     */
    public List<Pit> getUnupload() throws DbException {
        DbManager db = x.getDb(daoConfig);
        List<Pit> pits = db.selector(Pit.class).where("has_upload","=",false).findAll();
        return pits;
    }

    public void updateSuccess(int pitId) throws DbException {
        DbManager db = x.getDb(daoConfig);
        WhereBuilder whereBuilder = WhereBuilder.b("id","=",pitId);
        KeyValue keyValue = new KeyValue("has_upload",true);
        db.update(Pit.class,whereBuilder,keyValue);
    }
}

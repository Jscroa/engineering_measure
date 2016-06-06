package com.cx.measure.dao;

import org.xutils.DbManager;

/**
 * Created by yyao on 2016/6/6.
 */
public class BaseDao {

    protected DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("measure.db")
            .setDbVersion(1)
            .setDbOpenListener(new DbManager.DbOpenListener() {
        @Override
        public void onDbOpened(DbManager dbManager) {
            dbManager.getDatabase().enableWriteAheadLogging();
        }
    });
}

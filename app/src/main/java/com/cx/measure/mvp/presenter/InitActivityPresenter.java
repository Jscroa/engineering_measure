package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.Pit;
import com.cx.measure.dao.PitDao;
import com.cx.measure.mvp.view.InitActivityView;

import org.xutils.ex.DbException;

/**
 * Created by yyao on 2016/5/31.
 */
public class InitActivityPresenter implements HomeAsUpEnabledPresenter {

    private InitActivityView view;

    /**
     * 基坑初始化数据
     */
    private Pit pit;
    private double longitude;
    private double latitude;
    public InitActivityPresenter(InitActivityView view) {
        this.view = view;
        pit = new Pit();
    }

    public Pit getPit() {
        return pit;
    }

    public void setPit(Pit pit) {
        this.pit = pit;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public void back() {
        view.back();
    }

    public boolean save() {
        PitDao pitDao = new PitDao();
        try {
            pitDao.add(pit);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }
}

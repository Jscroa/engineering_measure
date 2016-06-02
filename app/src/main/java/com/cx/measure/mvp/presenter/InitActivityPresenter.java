package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.Pit;
import com.cx.measure.mvp.view.InitActivityView;

/**
 * Created by yyao on 2016/5/31.
 */
public class InitActivityPresenter implements HomeAsUpEnabledPresenter {

    private InitActivityView view;

    /**
     * 基坑初始化数据
     */
    private Pit pit;

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

    @Override
    public void back() {
        view.back();
    }



}

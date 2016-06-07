package com.cx.measure.mvp.presenter;

import com.cx.measure.mvp.view.MeasureBySelectActivityView;

/**
 * Created by yyao on 2016/6/7.
 */
public class MeasureBySelectActivityPresenter implements HomeAsUpEnabledPresenter {

    MeasureBySelectActivityView view;

    public MeasureBySelectActivityPresenter(MeasureBySelectActivityView view) {
        this.view = view;
    }

    @Override
    public void back() {
        view.back();
    }
}

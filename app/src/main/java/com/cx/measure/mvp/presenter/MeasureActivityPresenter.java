package com.cx.measure.mvp.presenter;

import com.cx.measure.mvp.view.MeasureActivityView;

/**
 * Created by yyao on 2016/5/31.
 */
public class MeasureActivityPresenter implements HomeAsUpEnabledPresenter {
    MeasureActivityView view;

    public MeasureActivityPresenter(MeasureActivityView view) {
        this.view = view;
    }

    @Override
    public void back() {
        view.back();
    }
}

package com.cx.measure.mvp.presenter;

import com.cx.measure.mvp.view.MainActivityView;

/**
 * Created by yyao on 2016/5/31.
 */
public class MainActivityPresenter {
    MainActivityView view;

    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
    }

    public void clickToInit() {
        view.toInit();
    }

    public void clickToMeasure() {
        view.toMeasure();
    }

    public void clickToSelect(){
        view.toSelect();
    }
}

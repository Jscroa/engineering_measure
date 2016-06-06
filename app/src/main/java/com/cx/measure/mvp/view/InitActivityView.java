package com.cx.measure.mvp.view;

import com.cx.measure.mvp.presenter.InitActivityPresenter;

/**
 * Created by yyao on 2016/5/31.
 */
public interface InitActivityView extends HomeAsUpEnabledView {
    void step1To2();
    void step2To1();
    void step2To3(int position);
    void step3To2();
    void finishStep();
    void confirmBack();
    InitActivityPresenter getPresenter();
}

package com.cx.measure.mvp.view;

import com.cx.measure.mvp.presenter.InitActivityPresenter;

/**
 * Created by yyao on 2016/5/31.
 */
public interface InitFragment1View {
    void toStep2();
    void setPitName(String name);
    String getPitName();
    InitActivityPresenter getActivityPresenter();
}

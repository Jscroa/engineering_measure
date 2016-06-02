package com.cx.measure.mvp.view;

import com.cx.measure.mvp.presenter.InitActivityPresenter;

/**
 * Created by yyao on 2016/5/31.
 */
public interface InitFragment2View {
    void backToStep1();
    void toStep3();
    void setWorkbenchName(String name);
    String getWorkbenchName();
    InitActivityPresenter getActivityPresenter();
}

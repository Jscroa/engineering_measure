package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.Pit;
import com.cx.measure.mvp.view.InitFragment1View;

/**
 * Created by yyao on 2016/6/1.
 */
public class InitFragment1Presenter {
    private InitFragment1View view;

    public InitFragment1Presenter(InitFragment1View view) {
        this.view = view;
    }

    public void savePitName(){
        InitActivityPresenter presenter = view.getActivityPresenter();
        Pit pit = presenter.getPit();
        pit.setName(view.getPitName());
        presenter.setPit(pit);
    }

    /**
     * 恢复数据
     */
    public void restore(){
        InitActivityPresenter presenter = view.getActivityPresenter();
        view.setPitName(presenter.getPit().getName());
    }
}

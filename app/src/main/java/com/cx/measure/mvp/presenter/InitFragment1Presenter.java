package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.Pit;
import com.cx.measure.mvp.view.InitFragment1View;

/**
 * Created by yyao on 2016/6/1.
 */
public class InitFragment1Presenter {
    private InitFragment1View view;

    private String pitName;

    InitActivityPresenter activityPresenter;

    public InitFragment1Presenter(InitFragment1View view) {
        this.view = view;
        this.activityPresenter = view.getActivityPresenter();
        this.pitName = activityPresenter.getPit().getName();
    }

    public void save() {
        this.pitName = view.getPitName();
        Pit pit = activityPresenter.getPit();
        pit.setName(pitName);
        activityPresenter.setPit(pit);
    }

    /**
     * 恢复数据
     */
    public void restore() {
        view.setPitName(pitName);
    }
}

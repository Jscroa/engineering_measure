package com.cx.measure.mvp.view;

import com.cx.measure.bean.WorkPoint;
import com.cx.measure.mvp.presenter.InitActivityPresenter;

import java.util.List;

/**
 * Created by yyao on 2016/5/31.
 */
public interface InitFragment3View {
    void backToStep2();
    InitActivityPresenter getActivityPresenter();

    void setWorkPoints(List<WorkPoint> workPoints);

    List<WorkPoint> addBlankWorkPoint(WorkPoint workPoint);

    List<WorkPoint> removeWorkPoint(int position);

    void refreshAddWorkPointButtonText();
}

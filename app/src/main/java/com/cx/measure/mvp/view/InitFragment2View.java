package com.cx.measure.mvp.view;

import com.cx.measure.bean.Workbench;
import com.cx.measure.mvp.presenter.InitActivityPresenter;

import java.util.List;

/**
 * Created by yyao on 2016/5/31.
 */
public interface InitFragment2View {
    void backToStep1();
    void toStep3();
    InitActivityPresenter getActivityPresenter();

    void setWorkbenches(List<Workbench> workbenches);

    List<Workbench> getWorkbenches();

    List<Workbench> addBlankWorkbench(Workbench workbench);

    List<Workbench> removeWorkbench(int position);

    /**
     * 更新添加工位按钮的text
     */
    void refreshAddWorkbenchButtonText();
}

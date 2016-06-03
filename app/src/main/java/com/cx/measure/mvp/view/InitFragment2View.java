package com.cx.measure.mvp.view;

import android.widget.Button;

import com.cx.measure.adapter.WorkbenchesAdapter;
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

    WorkbenchesAdapter getWorkbenchesAdapter();
    void setAddWorkbenchButtonText(String text);
}

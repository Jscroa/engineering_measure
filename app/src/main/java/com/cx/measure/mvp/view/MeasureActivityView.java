package com.cx.measure.mvp.view;

import java.util.List;

/**
 * Created by yyao on 2016/5/31.
 */
public interface MeasureActivityView extends HomeAsUpEnabledView {
    void setValues(List<Double> values);
    void refresh();
}

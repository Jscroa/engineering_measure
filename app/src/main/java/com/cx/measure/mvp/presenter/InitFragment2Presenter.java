package com.cx.measure.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.cx.measure.R;
import com.cx.measure.adapter.WorkbenchesAdapter;
import com.cx.measure.bean.Workbench;
import com.cx.measure.mvp.view.InitFragment2View;

import java.util.List;

/**
 * Created by yyao on 2016/6/1.
 */
public class InitFragment2Presenter {
    private InitFragment2View view;

    public InitFragment2Presenter(InitFragment2View view) {
        this.view = view;
    }


    /**
     * 恢复数据
     */
    public void restore(){

    }

    public void addBlankWorkbench(Context context){
        WorkbenchesAdapter adapter = view.getWorkbenchesAdapter();
        List<Workbench> workbenches = adapter.getWorkbenches();
        workbenches.add(new Workbench());
        adapter.setWorkbenches(workbenches);
        adapter.notifyDataSetChanged();
        view.setAddWorkbenchButtonText(context.getResources().getString(R.string.add_workbench)+" ("+workbenches.size()+")");
    }
}

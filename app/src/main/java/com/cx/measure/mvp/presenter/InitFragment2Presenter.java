package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.Pit;
import com.cx.measure.bean.Workbench;
import com.cx.measure.mvp.view.InitFragment2View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/1.
 */
public class InitFragment2Presenter {
    private InitFragment2View view;

    private InitActivityPresenter activityPresenter;

    private List<Workbench> workbenches;

    public InitFragment2Presenter(InitFragment2View view) {
        this.view = view;
        this.activityPresenter = view.getActivityPresenter();
        this.workbenches = activityPresenter.getPit().getWorkbenches();
    }

    private void save() {
        Pit pit = activityPresenter.getPit();
        pit.setWorkbenches(workbenches);
        activityPresenter.setPit(pit);
    }

    /**
     * 恢复数据
     */
    public void restore() {
        /*List<Workbench> */workbenches = activityPresenter.getPit().getWorkbenches();
        if (workbenches == null) {
            workbenches = new ArrayList<>();
        }
        view.setWorkbenches(workbenches);
        view.refreshAddWorkbenchButtonText();
    }


    public void addBlankWorkbench() {
        this.workbenches = view.addBlankWorkbench(new Workbench());
        view.refreshAddWorkbenchButtonText();
        save();
    }

    public void removeWorkbench(int position){
        this.workbenches = view.removeWorkbench(position);
        view.refreshAddWorkbenchButtonText();
        save();
    }

    public void setWorkbench(int position, String name, String rfid, double longitude, double latitude) {
        Workbench workbench = workbenches.get(position);
        workbench.setName(name);
        workbench.setRFID(rfid);
        workbench.setLongitude(longitude);
        workbench.setLatitude(latitude);
        workbenches.set(position, workbench);
        view.setWorkbenches(workbenches);
        save();
    }
}

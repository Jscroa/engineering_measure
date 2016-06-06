package com.cx.measure.mvp.presenter;

import com.cx.measure.bean.Pit;
import com.cx.measure.bean.WorkPoint;
import com.cx.measure.bean.Workbench;
import com.cx.measure.comments.MeasureType;
import com.cx.measure.mvp.view.InitFragment3View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/1.
 */
public class InitFragment3Presenter {

    private InitFragment3View view;
    private InitActivityPresenter activityPresenter;
    private Integer workbenchPosition;
    private List<WorkPoint> workPoints;

    public InitFragment3Presenter(InitFragment3View view,Integer workbenchPosition) {
        this.view = view;
        this.activityPresenter = view.getActivityPresenter();
        List<Workbench> workbenches = activityPresenter.getPit().getWorkbenches();
        int workbenchCount = workbenches.size();
        if(workbenchPosition==null || workbenchPosition>=workbenchCount || workbenchPosition<0){
            throw new RuntimeException("参数不正确");
        }
        this.workbenchPosition = workbenchPosition;
        this.workPoints = activityPresenter.getPit().getWorkbenches().get(workbenchPosition).getWorkPoints();
    }

    private void save(){
        Pit pit = activityPresenter.getPit();
        List<Workbench> workbenches = pit.getWorkbenches();
        Workbench workbench = workbenches.get(workbenchPosition);

        workbench.setWorkPoints(workPoints);

        workbenches.set(workbenchPosition,workbench);
        pit.setWorkbenches(workbenches);
        activityPresenter.setPit(pit);
    }

    public void restore(){
        workPoints = activityPresenter.getPit().getWorkbenches().get(workbenchPosition).getWorkPoints();
        if(workPoints==null){
            workPoints = new ArrayList<>();
        }
        view.setWorkPoints(workPoints);
        view.refreshAddWorkPointButtonText();
    }

    public void addBlankWorkPoint(){
        this.workPoints = view.addBlankWorkPoint(new WorkPoint());
        view.refreshAddWorkPointButtonText();
        save();
    }

    public void removeWorkPoint(int position){
        this.workPoints= view.removeWorkPoint(position);
        view.refreshAddWorkPointButtonText();
        save();
    }

    public void setWorkPoint(int position, String name, MeasureType type,int measureCount,int deviationPercent){
        WorkPoint workPoint = workPoints.get(position);
        workPoint.setName(name);
        workPoint.setType(type);
        workPoint.setMeasureCount(measureCount);
        workPoint.setDeviationPercent(deviationPercent);
        workPoints.set(position,workPoint);
        view.setWorkPoints(workPoints);
        save();
    }
}

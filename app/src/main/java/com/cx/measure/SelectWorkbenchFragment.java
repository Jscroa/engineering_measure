package com.cx.measure;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.cx.measure.adapter.PitWorkbenchAdapter;
import com.cx.measure.mvp.presenter.SelectWorkbenchPresenter;
import com.cx.measure.mvp.view.MeasureBySelectActivityView;
import com.cx.measure.mvp.view.SelectWorkbenchView;

/**
 * Created by yyao on 2016/6/7.
 */
public class SelectWorkbenchFragment extends Fragment implements SelectWorkbenchView {
    private SelectWorkbenchPresenter presenter;
    private View contentView;
    private ExpandableListView lvPitsWorkbenches;
    private PitWorkbenchAdapter pitWorkbenchAdapter;

    private ExpandableListView.OnChildClickListener childClickListener = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            int workbenchId = presenter.getWorkbenchId(groupPosition,childPosition);
            toSelectWorkPoint(workbenchId);
            return true;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_select_workbench,container,false);
        presenter = new SelectWorkbenchPresenter(getContext(),this);
        initViews();
        return contentView;
    }

    private void initViews(){
        lvPitsWorkbenches = (ExpandableListView) contentView.findViewById(R.id.lv_pits_workbenches);
        pitWorkbenchAdapter = new PitWorkbenchAdapter(getContext());
        presenter.reqPits();

        lvPitsWorkbenches.setOnChildClickListener(childClickListener);
    }

    @Override
    public void toSelectWorkPoint(int workPointId) {
        if(getActivity() instanceof MeasureBySelectActivityView){
            MeasureBySelectActivityView v = (MeasureBySelectActivityView) getActivity();
            v.step1To2(workPointId);
        }
    }

    @Override
    public void refresh() {
        pitWorkbenchAdapter.setPits(presenter.getPits());
        lvPitsWorkbenches.setAdapter(pitWorkbenchAdapter);
    }
}

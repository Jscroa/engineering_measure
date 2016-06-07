package com.cx.measure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cx.measure.mvp.presenter.SelectWorkPointPresenter;
import com.cx.measure.mvp.view.SelectWorkPointView;

/**
 * Created by yyao on 2016/6/7.
 */
public class SelectWorkPointFragment extends Fragment implements SelectWorkPointView {

    public static final String ARG_WORK_POINT_ID = "arg_work_point_id";

    private SelectWorkPointPresenter presenter;
    private View contentView;

    private ListView lvWorkPoints;
    private ArrayAdapter<String> workPointsAdapter;

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int pointId = presenter.getPointId(position);
            Intent intent = new Intent(getActivity(),MeasureActivity.class);
            intent.putExtra(MeasureActivity.ARG_WORK_POINT_ID,pointId);
            startActivity(intent);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_select_work_point,container,false);
        int workpointId = getArguments().getInt(ARG_WORK_POINT_ID);
        presenter = new SelectWorkPointPresenter(this,workpointId);
        initViews();
        return contentView;
    }

    private void initViews(){
        lvWorkPoints = (ListView) contentView.findViewById(R.id.lv_work_points);
        workPointsAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,presenter.getWorkPointsNames());
        lvWorkPoints.setAdapter(workPointsAdapter);

        lvWorkPoints.setOnItemClickListener(itemClickListener);
    }
}

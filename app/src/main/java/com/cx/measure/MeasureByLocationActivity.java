package com.cx.measure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cx.measure.comments.LocationTask;
import com.cx.measure.mvp.presenter.MeasureByLocationActivityPresenter;
import com.cx.measure.mvp.view.MeasureByLocationActivityView;

import org.xutils.ex.DbException;

public class MeasureByLocationActivity extends AppCompatActivity implements MeasureByLocationActivityView {

    MeasureByLocationActivityPresenter presenter;
    private LocationTask locationTask;
    private ListView lvWorkbenches;
    private ArrayAdapter<String> workbenchNamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_by_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new MeasureByLocationActivityPresenter(this);

        initViews();
        locationTask = new LocationTask(this);
        locationTask.setCallBack(new LocationTask.OnLocationCallBack() {
            @Override
            public void onLocationProvider(String provider) {

            }

            @Override
            public void onGetLocation(double longitude, double latitude) {
                try {
                    workbenchNamesAdapter = new ArrayAdapter<String>(MeasureByLocationActivity.this,android.R.layout.simple_list_item_1,presenter.getWorkbenchNames(longitude,latitude));
                    lvWorkbenches.setAdapter(workbenchNamesAdapter);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
        locationTask.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTask.stopLocation();
    }

    private void initViews() {
        lvWorkbenches = (ListView) findViewById(R.id.lv_workbenches);
//        workbenchNamesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,presenter.getWorkbenchNames());
        lvWorkbenches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                presenter.getWorkbenchId(position);
            }
        });
    }

    @Override
    public void back() {
        finish();
    }
}

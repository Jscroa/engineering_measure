package com.cx.measure;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cx.measure.comments.LocationTask;
import com.cx.measure.mvp.presenter.MeasureByLocationActivityPresenter;
import com.cx.measure.mvp.view.MeasureByLocationActivityView;

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
            public void onGetLocation(final double longitude,final double latitude) {
                new AsyncTask<Void,Void,Void>(){

                    @Override
                    protected Void doInBackground(Void... params) {
                        workbenchNamesAdapter = new ArrayAdapter<>(MeasureByLocationActivity.this, android.R.layout.simple_list_item_1, presenter.getWorkbenchNames(MeasureByLocationActivity.this, longitude, latitude));
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        lvWorkbenches.setAdapter(workbenchNamesAdapter);
                    }
                }.execute();
//                workbenchNamesAdapter = new ArrayAdapter<>(MeasureByLocationActivity.this, android.R.layout.simple_list_item_1, presenter.getWorkbenchNames(MeasureByLocationActivity.this, longitude, latitude));
//                lvWorkbenches.setAdapter(workbenchNamesAdapter);
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(MeasureByLocationActivity.this,"获取定位信息失败",Toast.LENGTH_SHORT).show();
            }
        });
        locationTask.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTask.stopLocation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                presenter.back();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

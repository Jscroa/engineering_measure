package com.cx.measure;

import android.content.Intent;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.cx.measure.comments.LocationTask;
import com.cx.measure.mvp.presenter.MeasureByLocationActivityPresenter;
import com.cx.measure.mvp.view.MeasureByLocationActivityView;

public class MeasureByLocationActivity extends AppCompatActivity implements MeasureByLocationActivityView {

    MeasureByLocationActivityPresenter presenter;
    private LocationTask locationTask;
    private ListView lvWorkbenches;
    private ArrayAdapter<String> workbenchNamesAdapter;

    BDLocationListener bdLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            Toast.makeText(MeasureByLocationActivity.this,"定位成功:("+bdLocation.getLongitude()+":"+bdLocation.getLatitude()+")，"+bdLocation.getAddrStr(),Toast.LENGTH_SHORT).show();
            locationTask.unregisterListener(bdLocationListener);
            locationTask.stop();
            new AsyncTask<Void,Void,Void>(){

                @Override
                protected Void doInBackground(Void... params) {
                    workbenchNamesAdapter = new ArrayAdapter<>(MeasureByLocationActivity.this, android.R.layout.simple_list_item_1, presenter.getWorkbenchNames(MeasureByLocationActivity.this, bdLocation.getLongitude(), bdLocation.getLatitude()));
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    lvWorkbenches.setAdapter(workbenchNamesAdapter);
                }
            }.execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_by_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new MeasureByLocationActivityPresenter(this);

        initViews();
        locationTask = ((MyApplication)getApplication()).locationTask;
        locationTask.registerListener(bdLocationListener);
        locationTask.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTask.stop();
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
                int workPointId = presenter.getWorkbenchId(position);
                Intent intent = new Intent(MeasureByLocationActivity.this,SelectWorkPointActivity.class);
                intent.putExtra(SelectWorkPointActivity.ARG_WORK_POINT_ID,workPointId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void back() {
        finish();
    }
}

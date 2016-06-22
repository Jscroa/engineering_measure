package com.cx.measure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cx.measure.mvp.presenter.SelectWorkPointPresenter;
import com.cx.measure.mvp.view.SelectWorkPointView;

public class SelectWorkPointActivity extends AppCompatActivity implements SelectWorkPointView{
    public static final String ARG_RFID = "arg_rfid";
    public static final String ARG_WORK_POINT_ID = "arg_work_point_id";
    private SelectWorkPointPresenter presenter;


    private ListView lvWorkPoints;
    private ArrayAdapter<String> workPointsAdapter;

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int pointId = presenter.getPointId(position);
            Intent intent = new Intent(SelectWorkPointActivity.this,MeasureActivity.class);
            intent.putExtra(MeasureActivity.ARG_WORK_POINT_ID,pointId);
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_work_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String rfid = getIntent().getStringExtra(ARG_RFID);
        int workPointId = getIntent().getIntExtra(ARG_WORK_POINT_ID,0);
        if(rfid!=null && !"".equals(rfid)){
            presenter = new SelectWorkPointPresenter(this,this,rfid);
        }else if(workPointId!=0){
            presenter = new SelectWorkPointPresenter(this,this,workPointId);
        }
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initViews(){
        lvWorkPoints = (ListView) findViewById(R.id.lv_work_points);
        presenter.reqPoints();

        lvWorkPoints.setOnItemClickListener(itemClickListener);
    }
    @Override
    public void refresh() {
        workPointsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,presenter.getWorkPointsNames());
        lvWorkPoints.setAdapter(workPointsAdapter);
    }
}

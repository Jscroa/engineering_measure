package com.cx.measure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cx.measure.comments.MeasureType;
import com.cx.measure.mvp.presenter.MeasureActivityPresenter;
import com.cx.measure.mvp.view.MeasureActivityView;

import org.xutils.ex.DbException;

public class MeasureActivity extends AppCompatActivity implements MeasureActivityView {

    private static final String TAG = "MeasureActivity";

    public static final String ARG_WORK_POINT_ID = "ARG_WORK_POINT_ID";

    private MeasureActivityPresenter presenter;

    private LinearLayout llContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int workPointId = getIntent().getIntExtra(ARG_WORK_POINT_ID,0);
        try {
            presenter = new MeasureActivityPresenter(this,workPointId);
        } catch (DbException e) {
            e.printStackTrace();
            Toast.makeText(this,"获取数据时遇到错误",Toast.LENGTH_SHORT).show();
            finish();
        }
        initViews();
    }

    private void initViews(){
        llContent = (LinearLayout) findViewById(R.id.ll_content);

        MeasureType type = MeasureType.getType(presenter.getWorkPoint().getMeasureType());
        int count = presenter.getWorkPoint().getMeasureCount();
        if(type==null || count==0){
            Toast.makeText(this,"数据模板错误，测量方式不正确或无测量次数",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Log.i(TAG,"当前点位测量方式为："+ type.getName());
            Log.i(TAG,"当前点位测量次数："+ count);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.back();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void back() {
        Log.i(TAG, "back");
        finish();
    }
}

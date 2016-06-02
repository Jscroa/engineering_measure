package com.cx.measure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.cx.measure.mvp.presenter.MeasureActivityPresenter;
import com.cx.measure.mvp.view.MeasureActivityView;

public class MeasureActivity extends AppCompatActivity implements MeasureActivityView {

    private static final String TAG = "MeasureActivity";

    private MeasureActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new MeasureActivityPresenter(this);
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

    @Override
    public void back() {
        Log.i(TAG,"back");
        finish();
    }
}

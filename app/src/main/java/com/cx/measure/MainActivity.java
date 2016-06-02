package com.cx.measure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cx.measure.mvp.presenter.MainActivityPresenter;
import com.cx.measure.mvp.view.MainActivityView;

public class MainActivity extends AppCompatActivity implements MainActivityView{

    private static final String TAG = "MainActivity";

    MainActivityPresenter presenter;

    /** 初始化按钮 */
    Button btnInit;
    /** 测量按钮 */
    Button btnMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new MainActivityPresenter(this);
        initViews();
    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_btn_init:
                    presenter.clickToInit();
                    break;
                case R.id.main_btn_measure:
                    presenter.clickToMeasure();
                    break;
                default:
                    break;
            }
        }
    };

    private void initViews(){
        btnInit = (Button) findViewById(R.id.main_btn_init);
        btnMeasure = (Button) findViewById(R.id.main_btn_measure);

        btnInit.setOnClickListener(btnClickListener);
        btnMeasure.setOnClickListener(btnClickListener);
    }

    @Override
    public void toInit() {
        Log.i(TAG,"toInit");
        startActivity(new Intent(MainActivity.this,InitActivity.class));
    }

    @Override
    public void toMeasure() {
        Log.i(TAG,"toMeasure");
        startActivity(new Intent(MainActivity.this,MeasureActivity.class));
    }
}

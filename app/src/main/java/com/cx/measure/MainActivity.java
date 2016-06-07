package com.cx.measure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cx.measure.mvp.presenter.MainActivityPresenter;
import com.cx.measure.mvp.view.MainActivityView;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private static final String TAG = "MainActivity";

    MainActivityPresenter presenter;

    /**
     * 初始化按钮
     */
    Button btnInit;
    /**
     * 测量按钮，扫描RFID
     */
    Button btnMeasureWithExplain;
    /**
     * 测量按钮，选择工位
     */
    Button mainBtnMeasureWithSelect;

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_btn_init:
                    presenter.clickToInit();
                    break;
                case R.id.main_btn_measure_with_explain:
                    presenter.clickToMeasure();
                    break;
                case R.id.main_btn_measure_with_select:
                    presenter.clickToSelect();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new MainActivityPresenter(this);
        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            default:
                break;
        }
    }

    private void initViews() {
        btnInit = (Button) findViewById(R.id.main_btn_init);
        btnMeasureWithExplain = (Button) findViewById(R.id.main_btn_measure_with_explain);
        mainBtnMeasureWithSelect = (Button) findViewById(R.id.main_btn_measure_with_select);

        btnInit.setOnClickListener(btnClickListener);
        btnMeasureWithExplain.setOnClickListener(btnClickListener);
        mainBtnMeasureWithSelect.setOnClickListener(btnClickListener);
    }

    @Override
    public void toInit() {
        Log.i(TAG, "toInit");
        startActivity(new Intent(MainActivity.this, InitActivity.class));
    }

    @Override
    public void toMeasure() {
        Log.i(TAG, "toMeasure");
//        startActivity(new Intent(MainActivity.this, MeasureActivity.class));
        Snackbar.make(btnMeasureWithExplain,"未安装此模块",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void toSelect() {
        Log.i(TAG, "toSelect");
        startActivity(new Intent(MainActivity.this,MeasureBySelectActivity.class));
//        startActivityForResult(new Intent(MainActivity.this,SelectWorkbenchActivity.class),SelectWorkbenchActivity.REQ_CODE_REQUEST_WORKBENCH);
    }
}

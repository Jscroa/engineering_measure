package com.cx.measure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
     * 测量按钮，定位
     */
    Button btnMeasureWithLocation;
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
                case R.id.main_btn_measure_with_location:
                    presenter.clickToLocation();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        btnInit = (Button) findViewById(R.id.main_btn_init);
        btnMeasureWithExplain = (Button) findViewById(R.id.main_btn_measure_with_explain);
        btnMeasureWithLocation = (Button) findViewById(R.id.main_btn_measure_with_location);
        mainBtnMeasureWithSelect = (Button) findViewById(R.id.main_btn_measure_with_select);

        btnInit.setOnClickListener(btnClickListener);
        btnMeasureWithExplain.setOnClickListener(btnClickListener);
        btnMeasureWithLocation.setOnClickListener(btnClickListener);
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
        Snackbar.make(btnMeasureWithExplain, "未安装此模块", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void toLocation() {
        Log.i(TAG, "toLocation");
        startActivity(new Intent(MainActivity.this, MeasureByLocationActivity.class));
    }

    @Override
    public void toSelect() {
        Log.i(TAG, "toSelect");
        startActivity(new Intent(MainActivity.this, MeasureBySelectActivity.class));
    }
}

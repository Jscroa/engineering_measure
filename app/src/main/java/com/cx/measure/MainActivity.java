package com.cx.measure;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cx.measure.mvp.presenter.MainActivityPresenter;
import com.cx.measure.mvp.view.MainActivityView;
import com.cx.measure.view.MyProgressDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private static final String TAG = "MainActivity";
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;
    MainActivityPresenter presenter;

    MyProgressDialog myProgressDialog;

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

    /**
     * 基本信息文本（富文本）
     */
    TextView tvComment1;
    TextView tvComment2;

    IntentFilter intentFilter;

    /**
     * 扫描到的rfid临时存放处
     */
    String targetRfid;

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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            targetRfid = intent.getStringExtra("KEY_READ_CODE");

//            Intent intent1 = new Intent(MainActivity.this,SelectWorkPointActivity.class);
//            intent1.putExtra(SelectWorkPointActivity.ARG_RFID,rfid);
//            startActivity(intent1);


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
        intentFilter = new IntentFilter();
        intentFilter.hasAction("ACTION_READ_CODE");

        getPersimmions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateComment1();
        if (targetRfid == null || "".equals(targetRfid)) {
            return;
        }
        Intent intent1 = new Intent(MainActivity.this, SelectWorkPointActivity.class);
        intent1.putExtra(SelectWorkPointActivity.ARG_RFID, targetRfid);
        startActivity(intent1);
        targetRfid = "";
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

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
        myProgressDialog = new MyProgressDialog(this);

        btnInit = (Button) findViewById(R.id.main_btn_init);
        btnMeasureWithExplain = (Button) findViewById(R.id.main_btn_measure_with_explain);
        btnMeasureWithLocation = (Button) findViewById(R.id.main_btn_measure_with_location);
        mainBtnMeasureWithSelect = (Button) findViewById(R.id.main_btn_measure_with_select);
        tvComment1 = (TextView) findViewById(R.id.tv_comment1);
        tvComment2 = (TextView) findViewById(R.id.tv_comment2);

        btnInit.setOnClickListener(btnClickListener);
        btnMeasureWithExplain.setOnClickListener(btnClickListener);
        btnMeasureWithLocation.setOnClickListener(btnClickListener);
        mainBtnMeasureWithSelect.setOnClickListener(btnClickListener);

    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void toInit() {
        Log.i(TAG, "toInit");
        startActivity(new Intent(MainActivity.this, InitActivity.class));
    }

    @Override
    public void toMeasure() {
        Log.i(TAG, "toMeasure");
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.senter.demo.uhf2", "com.senter.demo.uhf.Activity0ModuleSelection");
            intent.setComponent(cn);
            startActivity(intent);
            try {
                registerReceiver(broadcastReceiver, intentFilter);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(btnMeasureWithExplain, "未安装此模块", Snackbar.LENGTH_SHORT).show();
        }

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

    @Override
    public void updateComment1() {
        tvComment1.setText(presenter.getComment(this, myProgressDialog));
        tvComment1.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void updateComment2() {

    }
}

package com.cx.measure;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.cx.measure.mvp.presenter.InitActivityPresenter;
import com.cx.measure.mvp.view.InitActivityView;

public class InitActivity extends AppCompatActivity implements InitActivityView {

    private static final String TAG = "InitActivity";

    private InitActivityPresenter presenter;

    private FragmentManager fm;
    private InitFragment1 initFragment1;
    private InitFragment2 initFragment2;
    private InitFragment3 initFragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new InitActivityPresenter(this);
        initViews();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            // 监听返回键
            case KeyEvent.KEYCODE_BACK:
                // 回退栈中fragment数量
                int backStackCount = fm.getBackStackEntryCount();
                if (backStackCount == 1) {
                    confirmBack();
                } else if (backStackCount == 2) {
                    step2To1();
                } else if (backStackCount == 3) {
                    step3To2();
                } else {
                    Log.i(TAG, "has no more fragments");
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }

    }

    private void initViews() {
        fm = getSupportFragmentManager();
        initFragment1 = new InitFragment1();
        initFragment2 = new InitFragment2();
        initFragment3 = new InitFragment3();
        fm.beginTransaction().replace(R.id.frame_init_step, initFragment1).addToBackStack("step1").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                confirmBack();
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

    @Override
    public void step1To2() {
        Log.i(TAG, "step1 -> step2");
        fm.beginTransaction().setCustomAnimations(R.anim.slide_in_right, 0).replace(R.id.frame_init_step, initFragment2).addToBackStack("step2").commit();
    }

    @Override
    public void step2To1() {
        Log.i(TAG, "step2 -> step1");
        fm.popBackStackImmediate();
    }

    @Override
    public void step2To3() {
        Log.i(TAG, "step2 -> step3");
        fm.beginTransaction().setCustomAnimations(R.anim.slide_in_right, 0).replace(R.id.frame_init_step, initFragment3).addToBackStack("step3").commit();
    }

    @Override
    public void step3To2() {
        Log.i(TAG, "step3 -> step2");
        fm.popBackStackImmediate();
    }

    @Override
    public void finishStep() {
        Log.i(TAG, "finishStep");
        Toast.makeText(this, "添加完成", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void confirmBack() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(R.string.confirm_back);
        dialog.setMessage(getResources().getString(R.string.back_explain));
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.back();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    @Override
    public InitActivityPresenter getPresenter() {
        return presenter;
    }

}
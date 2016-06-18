package com.cx.measure;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.cx.measure.mvp.presenter.MeasureBySelectActivityPresenter;
import com.cx.measure.mvp.view.MeasureBySelectActivityView;

public class MeasureBySelectActivity extends AppCompatActivity implements MeasureBySelectActivityView {

    private static final String TAG = "MeasureBySelectActivity";

    private MeasureBySelectActivityPresenter presenter;

    private FragmentManager fm;
    private SelectWorkbenchFragment selectWorkbenchFragment;
    private SelectWorkPointFragment selectWorkPointFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_by_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new MeasureBySelectActivityPresenter(this);
        initViews();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                int backStackCount = fm.getBackStackEntryCount();
                if (backStackCount == 1) {
                    back();
                } else if (backStackCount == 2) {
                    step2To1();
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
        selectWorkbenchFragment = new SelectWorkbenchFragment();
        selectWorkPointFragment = new SelectWorkPointFragment();
        fm.beginTransaction().replace(R.id.frame_measure_step, selectWorkbenchFragment).addToBackStack(null).commit();
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
    public void step1To2(int workbenchId) {
        Log.i(TAG,"step1To2");
        Bundle bundle = new Bundle();
        bundle.putInt(SelectWorkPointFragment.ARG_WORKBENCH_ID,workbenchId);
        selectWorkPointFragment.setArguments(bundle);
        fm.beginTransaction().setCustomAnimations(R.anim.slide_in_right, 0).replace(R.id.frame_measure_step,selectWorkPointFragment).addToBackStack(null).commit();
    }

    @Override
    public void step2To1() {
        fm.popBackStackImmediate();
    }

    @Override
    public void back() {
        finish();
    }
}

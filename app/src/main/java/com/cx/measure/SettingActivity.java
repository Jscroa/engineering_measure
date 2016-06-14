package com.cx.measure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cx.measure.mvp.presenter.SettingActivityPresenter;
import com.cx.measure.mvp.view.SettingActivityView;

public class SettingActivity extends AppCompatActivity implements SettingActivityView {

    private SettingActivityPresenter presenter;

    private EditText etHost;
    private Button btnConfirm;
    private Button btnClear;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_confirm:
                    String host = etHost.getText().toString().trim();
                    presenter.saveHost(getApplicationContext(),host);
                    break;
                case R.id.btn_clear:
                    presenter.saveHost(getApplicationContext(),"");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new SettingActivityPresenter(this);
        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.restoreHost(getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                back();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        etHost = (EditText) findViewById(R.id.et_host);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnClear = (Button) findViewById(R.id.btn_clear);

        btnConfirm.setOnClickListener(clickListener);
        btnClear.setOnClickListener(clickListener);
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void setHost(String host) {
        etHost.setText(host);
    }
}

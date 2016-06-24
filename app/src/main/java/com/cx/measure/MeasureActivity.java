package com.cx.measure;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cx.measure.bean.WorkPoint;
import com.cx.measure.comments.MeasureType;
import com.cx.measure.mvp.presenter.MeasureActivityPresenter;
import com.cx.measure.mvp.view.MeasureActivityView;

import java.util.ArrayList;
import java.util.List;

public class MeasureActivity extends AppCompatActivity implements MeasureActivityView {

    private static final String TAG = "MeasureActivity";

    public static final String ARG_WORK_POINT_ID = "ARG_WORK_POINT_ID";

    private MeasureActivityPresenter presenter;
    private Toolbar toolbar;
    private LinearLayout llContent;
    private TextView tvComment;
    private List<EditText> ets = new ArrayList<>();

    private static final int MSG_SAVE_SUCCESS = 100;
    private static final int MSG_SAVE_FAIL = 200;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SAVE_SUCCESS:
                    Toast.makeText(MeasureActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case MSG_SAVE_FAIL:
                    Toast.makeText(MeasureActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int workPointId = getIntent().getIntExtra(ARG_WORK_POINT_ID, 0);

            presenter = new MeasureActivityPresenter(this,this, workPointId);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        presenter.restore();
    }

    private void initViews() {
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        tvComment = (TextView) findViewById(R.id.tv_comment);
        presenter.reqPoint();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_measure, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.back();
                return true;
            case R.id.menu_finish:
                new Thread(saveDataRunnable).start();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Runnable saveDataRunnable = new Runnable() {
        @Override
        public void run() {
            List<Double> values = new ArrayList<>();
            for (EditText et : ets) {
                String valueStr = et.getText().toString().trim();
                double value = ("".equals(valueStr)) ? 0.0 : Double.parseDouble(valueStr);
                values.add(value);
            }

            Message msg = new Message();
            if (presenter.save(values)) {
                msg.what = MSG_SAVE_SUCCESS;
            } else {
                msg.what = MSG_SAVE_FAIL;
            }
            handler.sendMessage(msg);
        }
    };

    @Override
    public void back() {
        Log.i(TAG, "back");
        finish();
    }

    @Override
    public void setValues(List<Double> values) {
        if (values == null || values.size() == 0) {
            // 记录值为空
            return;
        }
        if (values.size() != ets.size()) {
            // 记录值的数量和模板不一样
            Snackbar.make(toolbar,"记录值不正确，需要重新录入！",Snackbar.LENGTH_INDEFINITE).setAction(R.string.confirm, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.cleanValues();
                }
            }).show();
            return;
        }
        for (int i = 0; i < ets.size(); i++) {
            String valueStr = values.get(i) + "";
            if ("0.0".equals(valueStr)) {
                valueStr = "";
            }
            EditText et = ets.get(i);
            et.setText(valueStr);
        }
    }

    @Override
    public void refresh() {
        WorkPoint workPoint = presenter.getWorkPoint();
        if(workPoint==null){
            Toast.makeText(this, "数据模板错误", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        MeasureType type = MeasureType.getType(workPoint.getMeasureType());
        int count = presenter.getWorkPoint().getMeasureCount();
        if (type == null || count == 0) {
            Toast.makeText(this, "数据模板错误，测量方式不正确或无测量次数", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            String text = "测量方式：" + type.getName() + "<br>测量次数：<span style=\"color:blue\">" + count + "</span>";
            tvComment.setText(Html.fromHtml(text));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin), getResources().getDimensionPixelSize(R.dimen.half_margin), getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin), 0);
            for (int i = 0; i < count; i++) {
                EditText et = new EditText(this);
                et.setLayoutParams(params);
                et.setSingleLine(true);
                et.setHint((i + 1) + "");
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                llContent.addView(et);
                ets.add(et);
            }
        }
    }
}

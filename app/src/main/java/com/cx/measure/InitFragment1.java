package com.cx.measure;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cx.measure.mvp.presenter.InitActivityPresenter;
import com.cx.measure.mvp.presenter.InitFragment1Presenter;
import com.cx.measure.mvp.view.InitActivityView;
import com.cx.measure.mvp.view.InitFragment1View;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitFragment1 extends Fragment implements InitFragment1View {

    private static final String TAG = "InitFragment1";

    private InitFragment1Presenter presenter;

    private View contentView;

    /**
     * 下一步按钮
     */
    private Button btnNext;
    /**
     * 基坑名
     */
    private EditText etPit;


    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_next:
                    toStep2();
                    break;
                default:
                    break;
            }
        }
    };

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String pitName = etPit.getText().toString();
            if (pitName == null || "".equals(pitName)) {
                btnNext.setEnabled(false);
            } else {
                btnNext.setEnabled(true);
            }
        }
    };

    public InitFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        contentView = inflater.inflate(R.layout.fragment_init1, container, false);
        presenter = new InitFragment1Presenter(this);
        initViews();
        return contentView;
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        // 恢复数据
        presenter.restore();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();

    }

    private void initViews() {
        btnNext = (Button) contentView.findViewById(R.id.btn_next);
        etPit = (EditText) contentView.findViewById(R.id.et_pit);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(btnClickListener);
        etPit.addTextChangedListener(textWatcher);
    }

    @Override
    public void toStep2() {
        if (getActivity() instanceof InitActivityView) {
            InitActivityView v = (InitActivityView) getActivity();
            presenter.save();
            v.step1To2();
        }
    }

    @Override
    public void setPitName(String name) {
        etPit.setText(name);
    }

    @Override
    public String getPitName() {
        return etPit.getText().toString().trim();
    }

    @Override
    public InitActivityPresenter getActivityPresenter() {
        if (getActivity() instanceof InitActivityView) {
            InitActivityView v = (InitActivityView) getActivity();
            return v.getPresenter();
        } else {
            throw new RuntimeException("未找到对应的Activity");
        }
    }

}

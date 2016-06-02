package com.cx.measure;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cx.measure.mvp.presenter.InitActivityPresenter;
import com.cx.measure.mvp.presenter.InitFragment2Presenter;
import com.cx.measure.mvp.view.InitActivityView;
import com.cx.measure.mvp.view.InitFragment2View;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitFragment2 extends Fragment implements InitFragment2View {

    private static final String TAG = "InitFragment2";

    private InitFragment2Presenter presenter;

    private View contentView;

    /** 上一步按钮 */
    private Button btnPrevious;
    /** 下一步按钮 */
    private Button btnNext;

    private EditText etWorkbench;

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_previous:
                    backToStep1();
                    break;
                case R.id.btn_next:
                    toStep3();
                    break;
                default:
                    break;
            }
        }
    };

    public InitFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_init2, container, false);
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        presenter = new InitFragment2Presenter(this);
        initViews(contentView);
        return contentView;
    }

    private void initViews(View view) {
        btnPrevious = (Button) view.findViewById(R.id.btn_previous);
        btnNext = (Button) view.findViewById(R.id.btn_next);
        etWorkbench = (EditText) view.findViewById(R.id.et_workbench);
        btnPrevious.setOnClickListener(btnClickListener);
        btnNext.setOnClickListener(btnClickListener);
    }

    @Override
    public void backToStep1() {
        if (getActivity() instanceof InitActivityView){
            Log.i(TAG,"toStep1");
            InitActivityView v = (InitActivityView) getActivity();
            v.step2To1();
        }
    }

    @Override
    public void toStep3() {
        if (getActivity() instanceof InitActivityView){
            Log.i(TAG,"toStep3");
            InitActivityView v = (InitActivityView) getActivity();
            v.step2To3();
        }
    }

    @Override
    public void setWorkbenchName(String name) {
        etWorkbench.setText(name);
    }

    @Override
    public String getWorkbenchName() {
        return etWorkbench.getText().toString().trim();
    }

    @Override
    public InitActivityPresenter getActivityPresenter(){
        if (getActivity() instanceof InitActivityView){
            InitActivityView v = (InitActivityView) getActivity();
            return v.getPresenter();
        }else{
            throw new RuntimeException("未找到对应的Activity");
        }

    }
}

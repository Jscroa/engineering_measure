package com.cx.measure;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cx.measure.mvp.presenter.InitActivityPresenter;
import com.cx.measure.mvp.presenter.InitFragment3Presenter;
import com.cx.measure.mvp.view.InitActivityView;
import com.cx.measure.mvp.view.InitFragment3View;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitFragment3 extends Fragment implements InitFragment3View {

    private static final String TAG = "InitFragment3";

    private InitFragment3Presenter presenter;

    private View contentView;

    /** 上一步按钮 */
    private Button btnPrevious;
    /** 完成按钮 */
    private Button btnFinish;

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_previous:
                    backToStep2();
                    break;
                case R.id.btn_finish:
                    finishStep();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_init3, container, false);
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        presenter = new InitFragment3Presenter(this);
        initViews(contentView);
        return contentView;
    }

    private void initViews(View view) {
        btnPrevious = (Button) view.findViewById(R.id.btn_previous);
        btnFinish = (Button) view.findViewById(R.id.btn_finish);
        btnPrevious.setOnClickListener(btnClickListener);
        btnFinish.setOnClickListener(btnClickListener);
    }

    @Override
    public void backToStep2() {
        if (getActivity() instanceof InitActivityView){
            Log.i(TAG,"toStep2");
            InitActivityView v = (InitActivityView) getActivity();
            v.step3To2();
        }
    }

    @Override
    public void finishStep() {
        if (getActivity() instanceof InitActivityView){
            Log.i(TAG,"toStep2");
            InitActivityView v = (InitActivityView) getActivity();
            v.finishStep();
        }
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

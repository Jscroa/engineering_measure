package com.cx.measure;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.cx.measure.adapter.WorkPointAdapter;
import com.cx.measure.bean.WorkPoint;
import com.cx.measure.comments.MeasureType;
import com.cx.measure.mvp.presenter.InitActivityPresenter;
import com.cx.measure.mvp.presenter.InitFragment3Presenter;
import com.cx.measure.mvp.view.InitActivityView;
import com.cx.measure.mvp.view.InitFragment3View;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitFragment3 extends Fragment implements InitFragment3View {

    private static final String TAG = "InitFragment3";

    public static final String ARG_WORKBENCH_POSITION = "arg_workbench_position";

    private InitFragment3Presenter presenter;

    private View contentView;

    /**
     * 上一步按钮
     */
    private Button btnPrevious;
    private ListView lvWorkPoints;
    private WorkPointAdapter workPointAdapter;
    private Button btnAddWorkPoint;

    private ArrayAdapter<String> measureTypeAdapter;

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_previous:
                    backToStep2();
                    break;
                case R.id.btn_add_workpoint:
                    presenter.addBlankWorkPoint();
                    break;
                default:
                    break;
            }
        }
    };

    AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
            dialog.setTitle(R.string.confirm_remove);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    presenter.removeWorkPoint(position);
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
            return true;
        }
    };

    WorkPointAdapter.WorkPointAdapterCallback workPointAdapterCallback = new WorkPointAdapter.WorkPointAdapterCallback() {
        @Override
        public void onEditPointClick(final int position) {
            AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
            dialog.setTitle(R.string.workpoint);
            View dialogView = View.inflate(getContext(), R.layout.view_workpoint, null);
            final EditText etName = (EditText) dialogView.findViewById(R.id.et_name);
            final Spinner spinnerMeasureType = (Spinner) dialogView.findViewById(R.id.spinner_measure_type);
            final EditText etMeasureCount = (EditText) dialogView.findViewById(R.id.et_measure_count);
            final EditText etDeviation = (EditText) dialogView.findViewById(R.id.et_deviation);

            spinnerMeasureType.setAdapter(measureTypeAdapter);
            WorkPoint workPoint = workPointAdapter.getWorkPoints().get(position);
            etName.setText(workPoint.getName());
            MeasureType measureType = MeasureType.getType(workPoint.getMeasureType());
            if (measureType != null) {
                spinnerMeasureType.setSelection(measureType.getPositioon(measureType.getCode()));
            }
            etMeasureCount.setText(String.valueOf(workPoint.getMeasureCount()));
            etDeviation.setText(String.valueOf(workPoint.getDeviationPercent()));


            dialog.setView(dialogView);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getContext().getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = etName.getText().toString().trim();
                    MeasureType type = MeasureType.getByPosition(spinnerMeasureType.getSelectedItemPosition());
                    String measureCountStr = etMeasureCount.getText().toString().trim();
                    String deviationStr = etDeviation.getText().toString().trim();
                    int measureCount = ("".equals(measureCountStr)) ? 0 : Integer.parseInt(measureCountStr);
                    int deviation = ("".equals(deviationStr)) ? 0 : Integer.parseInt(deviationStr);
                    presenter.setWorkPoint(position, name, type, measureCount, deviation);
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_init3, container, false);
        // 获取启动参数
        int workbenchPosition = getArguments().getInt(ARG_WORKBENCH_POSITION);
        presenter = new InitFragment3Presenter(this, workbenchPosition);
        initViews(contentView);
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.restore();
        Log.i(TAG, "onResume");
    }

    private void initViews(View view) {
        btnPrevious = (Button) view.findViewById(R.id.btn_previous);
        lvWorkPoints = (ListView) view.findViewById(R.id.lv_workpoints);
        workPointAdapter = new WorkPointAdapter(getContext());
        workPointAdapter.setWorkPointAdapterCallback(workPointAdapterCallback);
        lvWorkPoints.setAdapter(workPointAdapter);
        btnAddWorkPoint = (Button) view.findViewById(R.id.btn_add_workpoint);

        btnPrevious.setOnClickListener(btnClickListener);
        lvWorkPoints.setOnItemLongClickListener(itemLongClickListener);
        btnAddWorkPoint.setOnClickListener(btnClickListener);

        measureTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, MeasureType.getNameList());
    }

    @Override
    public void backToStep2() {
        if (getActivity() instanceof InitActivityView) {
            Log.i(TAG, "toStep2");
            InitActivityView v = (InitActivityView) getActivity();
            v.step3To2();
        }
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

    @Override
    public void setWorkPoints(List<WorkPoint> workPoints) {
        workPointAdapter.setWorkPoints(workPoints);
        workPointAdapter.notifyDataSetChanged();
    }

    @Override
    public List<WorkPoint> addBlankWorkPoint(WorkPoint workPoint) {
        List<WorkPoint> workPoints = workPointAdapter.getWorkPoints();
        workPoints.add(workPoint);
        workPointAdapter.setWorkPoints(workPoints);
        workPointAdapter.notifyDataSetChanged();
        return workPoints;
    }

    @Override
    public List<WorkPoint> removeWorkPoint(int position) {

        List<WorkPoint> workPoints = workPointAdapter.getWorkPoints();
        workPoints.remove(position);
        workPointAdapter.setWorkPoints(workPoints);
        workPointAdapter.notifyDataSetChanged();
        return workPoints;
    }

    @Override
    public void refreshAddWorkPointButtonText() {
        String text = getResources().getString(R.string.add_workpoint);
        text = text + " (" + workPointAdapter.getCount() + ")";
        btnAddWorkPoint.setText(text);
    }
}

package com.cx.measure;

//import android.support.v7.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//
//import com.cx.measure.adapter.WorkbenchAdapter;
//import com.cx.measure.bean.Workbench;
//import com.cx.measure.mvp.presenter.InitActivityPresenter;
//import com.cx.measure.mvp.presenter.InitFragment2Presenter;
//import com.cx.measure.mvp.view.InitActivityView;
//import com.cx.measure.mvp.view.InitFragment2View;
//
//import java.util.List;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cx.measure.adapter.WorkbenchAdapter;
import com.cx.measure.bean.Workbench;
import com.cx.measure.mvp.presenter.InitActivityPresenter;
import com.cx.measure.mvp.presenter.InitFragment2Presenter;
import com.cx.measure.mvp.view.InitActivityView;
import com.cx.measure.mvp.view.InitFragment2View;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitFragment2 extends Fragment implements InitFragment2View {

    private static final String TAG = "InitFragment2";

    private InitFragment2Presenter presenter;

    private View contentView;

    /**
     * 上一步按钮
     */
    private Button btnPrevious;

    private ListView lvWorkbenches;
    private WorkbenchAdapter workbenchAdapter;
    private Button btnAddWorkbench;

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_previous:
                    backToStep1();
                    break;
                case R.id.btn_add_workbench:
                    presenter.addBlankWorkbench();
                    break;
                default:
                    break;
            }
        }
    };

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            toStep3(position);
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
                    presenter.removeWorkbench(position);
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

    WorkbenchAdapter.WorkbenchAdapterCallback workbenchAdapterCallback = new WorkbenchAdapter.WorkbenchAdapterCallback() {

        @Override
        public void onEditWorkbenchClick(final int position) {
            AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
            dialog.setTitle(R.string.workbench);
            View dialogView = View.inflate(getContext(), R.layout.view_workbench, null);
            final EditText etName = (EditText) dialogView.findViewById(R.id.et_name);
            final EditText etRfid = (EditText) dialogView.findViewById(R.id.et_rfid);
            final EditText etLongitude = (EditText) dialogView.findViewById(R.id.et_longitude);
            final EditText etLatitude = (EditText) dialogView.findViewById(R.id.et_latitude);

            Workbench workbench = workbenchAdapter.getWorkbenches().get(position);
            etName.setText(workbench.getName());
            etRfid.setText(workbench.getRFID());
            String longitude = "";
            if (workbench.getLongitude() != 0.0) {
                longitude = String.valueOf(workbench.getLongitude());
            }
            String latitude = "";
            if (workbench.getLatitude() != 0.0) {
                latitude = String.valueOf(workbench.getLatitude());
            }
            etLongitude.setText(longitude);
            etLatitude.setText(latitude);

            dialog.setView(dialogView);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getContext().getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = etName.getText().toString().trim();
                    String rfid = etRfid.getText().toString().trim();
                    String longitudeStr = etLongitude.getText().toString().trim();
                    String latitudeStr = etLatitude.getText().toString().trim();
                    double longitude = ("".equals(longitudeStr)) ? 0.0 : Double.parseDouble(longitudeStr);
                    double latitude = ("".equals(latitudeStr)) ? 0.0 : Double.parseDouble(latitudeStr);
                    presenter.setWorkbench(position, name, rfid, longitude, latitude);
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

    @Override
    public void onResume() {
        super.onResume();
        presenter.restore();
        Log.i(TAG, "onResume");
    }

    private void initViews(View view) {
        btnPrevious = (Button) view.findViewById(R.id.btn_previous);
        lvWorkbenches = (ListView) view.findViewById(R.id.lv_workbenches);
        workbenchAdapter = new WorkbenchAdapter(getContext());
        workbenchAdapter.setWorkbenchAdapterCallback(workbenchAdapterCallback);
        lvWorkbenches.setAdapter(workbenchAdapter);
        btnAddWorkbench = (Button) view.findViewById(R.id.btn_add_workbench);

        btnPrevious.setOnClickListener(btnClickListener);
        lvWorkbenches.setOnItemClickListener(itemClickListener);
        lvWorkbenches.setOnItemLongClickListener(itemLongClickListener);
        btnAddWorkbench.setOnClickListener(btnClickListener);
    }

    @Override
    public void backToStep1() {
        if (getActivity() instanceof InitActivityView) {
            Log.i(TAG, "toStep1");
            InitActivityView v = (InitActivityView) getActivity();
            v.step2To1();
        }
    }

    @Override
    public void toStep3(int position) {
        if (getActivity() instanceof InitActivityView) {
            Log.i(TAG, "toStep3");
            InitActivityView v = (InitActivityView) getActivity();
            v.step2To3(position);
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
    public void setWorkbenches(List<Workbench> workbenches) {
        workbenchAdapter.setWorkbenches(workbenches);
        workbenchAdapter.notifyDataSetChanged();
    }

    @Override
    public List<Workbench> addBlankWorkbench(Workbench workbench) {
        List<Workbench> workbenches = workbenchAdapter.getWorkbenches();
        workbenches.add(workbench);
        workbenchAdapter.setWorkbenches(workbenches);
        workbenchAdapter.notifyDataSetChanged();
        return workbenches;
    }

    @Override
    public List<Workbench> removeWorkbench(int position) {
        List<Workbench> workbenches = workbenchAdapter.getWorkbenches();
        workbenches.remove(position);
        workbenchAdapter.setWorkbenches(workbenches);
        workbenchAdapter.notifyDataSetChanged();
        return workbenches;
    }

    @Override
    public void refreshAddWorkbenchButtonText() {
        String text = getResources().getString(R.string.add_workbench);
        text = text + " (" + workbenchAdapter.getCount() + ")";
        btnAddWorkbench.setText(text);
    }
}

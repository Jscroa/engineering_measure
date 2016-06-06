package com.cx.measure.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cx.measure.R;
import com.cx.measure.bean.Workbench;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/5.
 */
public class WorkbenchAdapter extends BaseAdapter {
    private Context context;
    private List<Workbench> workbenches;

    WorkbenchAdapterCallback callback;

    public WorkbenchAdapter(Context context) {
        this.context = context;
        workbenches = new ArrayList<>();
    }

    public void setWorkbenchAdapterCallback(WorkbenchAdapterCallback callback) {
        this.callback = callback;
    }

    public List<Workbench> getWorkbenches() {
        return workbenches;
    }

    public void setWorkbenches(List<Workbench> workbenches) {
        this.workbenches = workbenches;
    }

    @Override
    public int getCount() {
        return workbenches.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_workbench, null);
            viewHolder = initViews(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Workbench workbench = workbenches.get(position);
        viewHolder.tvName.setText(workbench.getName());
        viewHolder.tvRfid.setText(workbench.getRFID());
        String location = "";
        if (workbench.getLongitude() != 0.0 || workbench.getLatitude() != 0.0) {
            location = "( " + workbench.getLongitude() + " : " + workbench.getLatitude() + " )";
        }
        viewHolder.tv_latitude_and_longitude.setText(location);

        BtnClickListener btnClickListener = new BtnClickListener(position);
        viewHolder.btnEditWorkbench.setOnClickListener(btnClickListener);
        viewHolder.btnEnterPoint.setOnClickListener(btnClickListener);

        return convertView;
    }

    private ViewHolder initViews(View convertView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
        viewHolder.tvRfid = (TextView) convertView.findViewById(R.id.tv_rfid);
        viewHolder.tv_latitude_and_longitude = (TextView) convertView.findViewById(R.id.tv_latitude_and_longitude);
        viewHolder.btnEditWorkbench = (Button) convertView.findViewById(R.id.btn_edit_workbench);
        viewHolder.btnEnterPoint = (Button) convertView.findViewById(R.id.btn_enter_point);
        return viewHolder;
    }

    private class ViewHolder {
        /**
         * 工位名
         */
        TextView tvName;
        /**
         * RFID
         */
        TextView tvRfid;
        /**
         * 经纬度
         */
        TextView tv_latitude_and_longitude;
        /**
         * 编辑工位
         */
        Button btnEditWorkbench;
        /**
         * 进入点位
         */
        Button btnEnterPoint;
    }

    private class BtnClickListener implements View.OnClickListener {

        private int position;

        public BtnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if(callback==null){
                return;
            }
            switch (v.getId()) {
                case R.id.btn_edit_workbench:
                    callback.onEditWorkbenchClick(position);
                    break;
                case R.id.btn_enter_point:
                    callback.onEditPointClick(position);
                    break;
                default:
                    break;
            }
        }
    }

    public interface WorkbenchAdapterCallback {
        void onEditWorkbenchClick(int position);

        void onEditPointClick(int position);
    }
}

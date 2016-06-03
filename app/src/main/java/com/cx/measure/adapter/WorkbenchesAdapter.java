package com.cx.measure.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cx.measure.R;
import com.cx.measure.bean.Workbench;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/3.
 */
public class WorkbenchesAdapter extends BaseAdapter {
    private Context context;
    private List<Workbench> workbenches;

    public WorkbenchesAdapter(Context context) {
        this.context = context;
        workbenches = new ArrayList<>();
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
            convertView = View.inflate(context, R.layout.view_workbench,null);
            viewHolder = initViews(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private ViewHolder initViews(View convertView){
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.etName = (EditText) convertView.findViewById(R.id.et_name);
        viewHolder.etRfid = (EditText) convertView.findViewById(R.id.et_rfid);
        viewHolder.ivRfidScan = (ImageView) convertView.findViewById(R.id.iv_rfid_scan);
        viewHolder.etLongitude = (EditText) convertView.findViewById(R.id.et_longitude);
        viewHolder.etLatitude = (EditText) convertView.findViewById(R.id.et_latitude);
        viewHolder.ivLocation = (ImageView) convertView.findViewById(R.id.iv_location);
        viewHolder.btnEditPoint = (Button) convertView.findViewById(R.id.btn_edit_point);
        return viewHolder;
    }

    private class ViewHolder {
        /** 工位名 */
        EditText etName;
        /** RFID */
        EditText etRfid;
        /** 扫描RFID */
        ImageView ivRfidScan;
        /** 经度 */
        EditText etLongitude;
        /** 纬度 */
        EditText etLatitude;
        /** 定位 */
        ImageView ivLocation;
        /** 编辑点位 */
        Button btnEditPoint;
    }
}

package com.cx.measure.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cx.measure.R;
import com.cx.measure.bean.WorkPoint;
import com.cx.measure.comments.MeasureType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyao on 2016/6/6.
 */
public class WorkPointAdapter extends BaseAdapter {

    private Context context;
    private List<WorkPoint> workPoints;

    WorkPointAdapterCallback callback;

    public WorkPointAdapter(Context context) {
        this.context = context;
        workPoints = new ArrayList<>();
    }

    public void setWorkPointAdapterCallback(WorkPointAdapterCallback callback) {
        this.callback = callback;
    }

    public List<WorkPoint> getWorkPoints() {
        return workPoints;
    }

    public void setWorkPoints(List<WorkPoint> workPoints) {
        this.workPoints = workPoints;
    }

    @Override
    public int getCount() {
        return workPoints.size();
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
            convertView = View.inflate(context, R.layout.item_workpoint, null);
            viewHolder = initViews(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WorkPoint workPoint = workPoints.get(position);
        viewHolder.tvName.setText(workPoint.getName());
        MeasureType type = MeasureType.getType(workPoint.getMeasureType());
        if (type == null) {
            viewHolder.tvType.setText("");
        } else {
            viewHolder.tvType.setText(type.getName());
        }
        viewHolder.tvCount.setText(String.valueOf(workPoint.getMeasureCount()));
        viewHolder.tvDeviation.setText("±" + workPoint.getDeviationPercent() + "%");

        BtnClickListener btnClickListener = new BtnClickListener(position);
        viewHolder.btnEditWorkPoint.setOnClickListener(btnClickListener);

        return convertView;
    }

    private ViewHolder initViews(View convertView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
        viewHolder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
        viewHolder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
        viewHolder.tvDeviation = (TextView) convertView.findViewById(R.id.tv_deviation);
        viewHolder.btnEditWorkPoint = (Button) convertView.findViewById(R.id.btn_edit_workpoint);
        return viewHolder;
    }

    private class ViewHolder {
        /**
         * 点位名
         */
        private TextView tvName;
        /**
         * 测量方法
         */
        private TextView tvType;
        /**
         * 测量次数
         */
        private TextView tvCount;
        /**
         * 误差范围
         */
        private TextView tvDeviation;
        /**
         * 编辑点位
         */
        private Button btnEditWorkPoint;
    }

    private class BtnClickListener implements View.OnClickListener {
        private int position;

        public BtnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (callback == null) {
                return;
            }
            switch (v.getId()) {
                case R.id.btn_edit_workpoint:
                    callback.onEditPointClick(position);
                    break;
                default:
                    break;
            }
        }
    }

    public interface WorkPointAdapterCallback {
        void onEditPointClick(int position);
    }

}

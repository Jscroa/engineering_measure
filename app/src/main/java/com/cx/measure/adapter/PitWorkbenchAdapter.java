package com.cx.measure.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.cx.measure.R;
import com.cx.measure.bean.Pit;
import com.cx.measure.bean.Workbench;

import java.util.List;

/**
 * Created by yyao on 2016/6/7.
 */
public class PitWorkbenchAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<Pit> pits;

    public PitWorkbenchAdapter(Context context) {
        this.context = context;
    }

    public void setPits(List<Pit> pits) {
        this.pits = pits;
    }

    @Override
    public int getGroupCount() {
        return pits.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Workbench> workbenches = pits.get(groupPosition).getWorkbenches();
        if(workbenches==null){
            return 0;
        }
        return workbenches.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition+"";
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupPosition+"_"+childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return pits.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        List<Workbench> workbenches = pits.get(groupPosition).getWorkbenches();
        if(workbenches!=null && workbenches.size()>childPosition){
            return workbenches.get(childPosition).getId();
        }
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = new TextView(context);
            convertView.setPadding(128,32,32,32);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorLightWhite));
        }
        ((TextView)convertView).setText("基坑："+pits.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = new TextView(context);
            convertView.setPadding(256,32,32,32);
        }
        List<Workbench> workbenches = pits.get(groupPosition).getWorkbenches();
        if(workbenches!=null && workbenches.size()>childPosition){
            ((TextView)convertView).setText("工位："+workbenches.get(childPosition).getName());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

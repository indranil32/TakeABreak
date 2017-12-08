package com.example.indranilm.takeabreak;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by indranilm on 5/12/17.
 */

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<Object> children;
    private LayoutInflater inflater;
    private ArrayList<String> groups;
    protected TimePicker timePicker;
    protected TextView alarmTextView;
    protected ToggleButton alarmToggle;


    public CustomExpandableListAdapter(ArrayList<String> parents, ArrayList<Object> children) {
        this.groups = parents;
        this.children = children;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) children.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_tabs, null);
        }
        ((CheckedTextView) convertView).setText(groups.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            if (groupPosition == 0) {
                convertView = inflater.inflate(R.layout.custom_tab_quick_alarm, null);
                this.timePicker = (TimePicker) convertView.findViewById(R.id.alarmTimePicker);
                this.alarmTextView = (TextView) convertView.findViewById(R.id.alarmText);
                this.alarmToggle = (ToggleButton) convertView.findViewById(R.id.alarmToggle);
            }
            if (groupPosition == 1){
                convertView = inflater.inflate(R.layout.custom_tab_quick_alarm, null);
            }
            if (groupPosition == 2) {
                convertView = inflater.inflate(R.layout.custom_tab_quick_alarm, null);
            }
        }



        //textView = (TextView) convertView.findViewById(R.id.);
        //textView.setText(child.get(childPosition));

        /*convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(activity, child.get(childPosition),
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        return convertView;
    }




    /*public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d("MyActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }*/
}

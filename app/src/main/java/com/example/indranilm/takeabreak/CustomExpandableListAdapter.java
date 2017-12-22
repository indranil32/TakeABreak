package com.example.indranilm.takeabreak;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by indranilm on 5/12/17.
 */

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<String> groups;
    private SharedPreferences settings;

    //protected TimePicker timePicker;
    //protected TextView alarmTextView;
    //protected ToggleButton alarmToggle;
    protected EditText interval;
    protected TextView nextBreak;
    protected int minutes;
    protected String newTime;

    private static final String SETTINGS_DATA = "takeabreak_settings";
    private static final String INTERVAL = "takeabreak_settings_interval";
    private static final String ALARM_TIME = "takeabreak_settings_alarm";
    private static final String ONE_TIME = "onetime";

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
        //if (convertView == null) {
            /*if (groupPosition == 0) {
                convertView = inflater.inflate(R.layout.custom_tab_quick_alarm, null);
                this.timePicker = (TimePicker) convertView.findViewById(R.id.alarmTimePicker);
                this.alarmTextView = (TextView) convertView.findViewById(R.id.alarmText);
                this.alarmToggle = (ToggleButton) convertView.findViewById(R.id.alarmToggle);
            }*/
            if (groupPosition == 0){
                convertView = inflater.inflate(R.layout.settings, null);
                this.interval = (EditText) convertView.findViewById(R.id.interval);
                String settingsStr = String.valueOf(this.minutes);
                // on start
                this.interval.setText(settingsStr.subSequence(0,settingsStr.length()));
                // on change
                this.interval.addTextChangedListener(new TextWatcher()
                {
                    public void afterTextChanged(Editable s) {
                        saveData();
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                });
            }
            if (groupPosition == 1) {
                convertView = inflater.inflate(R.layout.next_break, null);
                this.nextBreak = (TextView) convertView.findViewById(R.id.nextBreak);
                if (this.newTime == null) {
                    this.newTime = this.settings.getString(ALARM_TIME,"Not yet set!");
                }
                this.nextBreak.setText("The next break should be at - "+ this.newTime);
            }
        //}
     return convertView;
    }

    private void saveData() {
        if (this.interval != null &&
                this.interval.getText() != null &&
                !this.interval.getText().toString().isEmpty() &&
                this.interval.getText().toString().trim().length() == 2) {
            this.minutes = Integer.parseInt(this.interval.getText().toString());
            SharedPreferences.Editor editor = this.settings.edit();
            editor.putInt(INTERVAL, this.minutes);
            // Commit the edits!
            editor.commit();
            System.out.println("saveData >> minutes " + minutes);
            // new time
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, this.minutes);
            this.newTime = new SimpleDateFormat("h:mm a").format(cal.getTime());
            editor.putString(ALARM_TIME, this.newTime);
            // Commit the edits!
            editor.commit();
            System.out.println("saveData >> newTime "+ this.newTime);

            // cancel the earlier alarm if any
            cancelAlarm();
            //set the new alarm
            setAlarm(this.minutes * 60 * 1000);
        }
    }


    private void setAlarm(long interval)
    {
        System.out.println("setAlarm");
        Context context = this.activity.getBaseContext();
        AlarmManager am = (AlarmManager) this.activity.getSystemService(this.activity.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+interval, interval , pi);
    }

    public void cancelAlarm()
    {
        System.out.println("CancelAlarm");
        Context context = this.activity.getBaseContext();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public CustomExpandableListAdapter(ArrayList<String> parents) {
        this.groups = parents;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
        this.settings = this.activity.getSharedPreferences(SETTINGS_DATA, 0);
        this.minutes = this.settings.getInt(INTERVAL,20);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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

}

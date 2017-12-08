package com.example.indranilm.takeabreak;

import android.app.AlarmManager;
import android.app.ExpandableListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends ExpandableListActivity {

    private AlarmManagerBroadcastReceiver alarm;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private ArrayList<String> groups = new ArrayList<String>();
    private ArrayList<Object> children = new ArrayList<Object>();
    private CustomExpandableListAdapter adapter;

    private static MainActivity inst;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("start onCreate");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ExpandableListView expandableList = getExpandableListView();

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        adapter = new CustomExpandableListAdapter(groups, children);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //alarm = new AlarmManagerBroadcastReceiver();
        //Context context = this.getApplicationContext();
        //alarm.SetAlarm(context, 10);
    }


    @Override
    protected void onStart() {
        System.out.println("start onStart");
        super.onStart();
        inst = this;
    }

    /*public void snooze(View view) {
        System.out.println("start snooze");
        Context context = this.getApplicationContext();
        if (alarm != null)
            alarm.CancelAlarm(context);
        this.finish();
        System.exit(0);
    }*/


    public void setGroupParents() {
        groups.add("Quick Alarm");
        groups.add("Settings");
        groups.add("View");
    }

    public void setChildData() {

        ArrayList<String> child = new ArrayList<String>();
        child.add("Set");
        children.add(child);

        child = new ArrayList<String>();
        child.add("Settings");
        children.add(child);

        child = new ArrayList<String>();
        child.add("List");
        children.add(child);
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, adapter.timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, adapter.timePicker.getCurrentMinute());
            Intent alarmIntent = new Intent(MainActivity.this, AlarmManagerBroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
        }
    }

    public void setAlarmText(String alarmText) {
        if (adapter != null) adapter.alarmTextView.setText(alarmText);
    }

    protected void sendNotification(String msg) {
        NotificationManager alarmNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
    }
}

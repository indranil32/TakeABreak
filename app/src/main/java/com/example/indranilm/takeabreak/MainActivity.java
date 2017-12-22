package com.example.indranilm.takeabreak;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class MainActivity extends ExpandableListActivity {

    private ArrayList<String> groups = new ArrayList<String>();
    private CustomExpandableListAdapter adapter;

    private static MainActivity inst;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setGroupParents();

        ExpandableListView expandableList = getExpandableListView();
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        adapter = new CustomExpandableListAdapter(groups);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),this);

        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    private void setGroupParents() {
        //groups.add("Quick Alarm");
        groups.add("Settings");
        groups.add("View");
    }


    /*
    public void snooze(View view) {
        System.out.println("start snooze");
        this.adapter.cancelAlarm();
    }


    public void setOnetimeTimer(Context context, long time){
        System.out.println("setOnetimeTimer");
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
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
    }*/
}

package com.example.indranilm.takeabreak;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class MainActivity extends ExpandableListActivity {

    private AlarmManagerBroadcastReceiver alarm;
    private ArrayList<String> groups = new ArrayList<String>();
    private ArrayList<Object> children = new ArrayList<Object>();
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

        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(groups, children);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);

        //alarm = new AlarmManagerBroadcastReceiver();
        //Context context = this.getApplicationContext();
        //alarm.SetAlarm(context, 10);
    }


    @Override
    protected void onStart() {
        System.out.println("start onStart");
        super.onStart();
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


}

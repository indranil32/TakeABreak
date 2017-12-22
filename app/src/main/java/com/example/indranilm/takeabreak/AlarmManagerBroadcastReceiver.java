package com.example.indranilm.takeabreak;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

;

/**
 * Created by indranilm on 23/5/17.
 */

public class AlarmManagerBroadcastReceiver extends WakefulBroadcastReceiver {//BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("onReceive");
        // this will update the UI with message
        try {
            MainActivity inst = MainActivity.instance();
            //inst.setAlarmText("Alarm! Wake up! Wake up!");

            // this will sound the alarm tone
            // this will sound the alarm once, if you wish to
            // raise alarm in loop continuously then use MediaPlayer and

            Uri alarmUri = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();

            // this will send a notification message
            ComponentName comp = new ComponentName(context.getPackageName(),
                    AlarmService.class.getName());
            intent.setComponent(comp);
            // If extended by BroadcastReceiver class then comment this code
            startWakefulService(context, (intent.setComponent(comp)));

            setResultCode(Activity.RESULT_OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        /*PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Take a break!");
        //Acquire the lock
        wl.acquire();

        //You can do the processing here.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();

        if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
            //Make sure this intent has been sent by the one-time timer button.
            msgStr.append("Take a break!");
        }

        //Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
        Intent int2 = new Intent(context, MainActivity.class);
        int2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(int2);
        //Release the lock
        wl.release();*/
    }

     /*@Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
        MainActivity inst = MainActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //this will send a notification message
        //ComponentName comp = new ComponentName(context.getPackageName(),
        //        AlarmService.class.getName());
        //startWakefulService(context, (intent.setComponent(comp)));
        inst.sendNotification("Move moeve move....");
        setResultCode(Activity.RESULT_OK);
    }*/
}

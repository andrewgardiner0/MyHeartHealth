package com.example.andrewgardiner.myhearthealth;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Andrew gardiner on 24/02/2016.
 */
public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String drugName;
        String drugStrength;
        drugName = intent.getStringExtra("drugName");
        drugStrength = intent.getStringExtra("drugStrength");

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        Toast.makeText(context, "it's time to take " + drugName + " " + drugStrength, Toast.LENGTH_LONG).show(); // For example

        wl.release();
    }

    public void SetAlarm(Context context, int hour, int minute, ArrayList<Integer> days, String drugName, String drugStrength)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        for(int i=0;i<days.size()-1;i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.HOUR_OF_DAY, hour);
            calendar.add(calendar.MINUTE, minute);
            calendar.add(Calendar.DAY_OF_WEEK, days.get(i));

            Intent intent = new Intent(context, Alarm.class);
            intent.putExtra("drugName", drugName);
            intent.putExtra("drugStrength", drugStrength);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        }// Millisec * Second * Minute
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}

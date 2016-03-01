package com.example.andrewgardiner.myhearthealth;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

public class PillScheduler extends AppCompatActivity {
    private TextView drugName;
    private TextView drugStrength;
    private TextView timeofday;

    private RadioButton mon;
    private RadioButton tue;
    private RadioButton wed;
    private RadioButton thur;
    private RadioButton fri;
    private RadioButton sat;
    private RadioButton sun;

    private Button ok;
    private ArrayList<Integer> days = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_scheduler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drugName = (TextView) findViewById(R.id.txtDrugName);
        drugStrength = (TextView) findViewById(R.id.txtStrength);
        timeofday = (TextView) findViewById(R.id.txtTime);
        mon = (RadioButton) findViewById(R.id.monday);
        tue = (RadioButton) findViewById(R.id.tuesday);
        wed = (RadioButton) findViewById(R.id.wednesday);
        thur = (RadioButton) findViewById(R.id.thursday);
        fri = (RadioButton) findViewById(R.id.friday);
        sat = (RadioButton) findViewById(R.id.saturday);
        sun = (RadioButton) findViewById(R.id.sunday);
        ok = (Button) findViewById(R.id.btnAddDrug);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Alarm alarm = new Alarm();
                final Intent intent = new Intent();
                intent.putIntegerArrayListExtra("days", days);
                intent.putExtra("drugName", drugName.toString());
                intent.putExtra("drugStrength", drugStrength.toString());
                intent.putExtra("hour",hour());
                intent.putExtra("minute", minute());
                alarm.SetAlarm(v.getContext(), hour(), minute(), days, drugName.toString(), drugStrength.toString());
                Toast.makeText(v.getContext(), "notification set", Toast.LENGTH_LONG).show();
                */

                showNotificationClicked(v);


            }
        });






    }

    public void showNotificationClicked(View v) {
        String notificationText = "It's time to take" + drugName.toString() + drugStrength.toString();
        scheduleNotification(getNotification(notificationText));
    }
    private void scheduleNotification(Notification notification) {
        Calendar cal =  Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.HOUR_OF_DAY ,15);
       // cal.add(Calendar.MINUTE, );
        //cal.add(Calendar.DAY_OF_WEEK,5);



        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //long futureInMillis = SystemClock.elapsedRealtime();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Pills");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher);
        return builder.build();
    }




    public int hour(){
        int hour = 0;
        String delim = ":";
        String text = timeofday.getText().toString();

        String[] tokens = text.split(delim);
        hour = Integer.parseInt(tokens[0]);


        return hour;
    }
    public int minute(){
        int minute = 0;
        String delim = ":";
        String text = timeofday.getText().toString();

        String[] tokens = text.split(delim);
        minute = Integer.parseInt(tokens[1]);

        return minute;

    }

    public void onRadioButtonClicked(View view) {
        boolean checked =((RadioButton) view).isChecked();
        int m = 2;
        int t = 3;
        int w = 4;
        int th = 5;
        int f = 6;
        int s = 7;
        int su = 1;

        switch (view.getId()){
            case R.id.monday:
            days.add(0,2);
            case R.id.tuesday:
                days.add(0,3);
            case R.id.wednesday:
                days.add(0,4);
            case R.id.thursday:
                days.add(0,5);
            case R.id.friday:
                days.add(0,6);
            case R.id.saturday:
                days.add(0,7);
            case R.id.sunday:
                days.add(0,1);


        }
    }


}

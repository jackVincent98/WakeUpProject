package com.example.jackv.wakeup;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AddAlarm extends AppCompatActivity {

    TimePicker chosenTime;
    Button startAlarm;
    TextView alarmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chosenTime = findViewById(R.id.alarmTimePicker);
        startAlarm = findViewById(R.id.start_alarm);
        alarmText = findViewById(R.id.alarmText);

        startAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    alarmText = findViewById(R.id.alarmText);
                    alarmText.setText("Alarm on");
                    Calendar cal = Calendar.getInstance();
                    cal.set (Calendar.HOUR_OF_DAY, chosenTime.getCurrentHour());
                    cal.set (Calendar.MINUTE, chosenTime.getCurrentMinute());
                    setAlarm(cal);
            }
        });
    }

    public void setAlarm(Calendar targetTime){
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(AddAlarm.this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(AddAlarm.this, 0, alarmIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetTime.getTimeInMillis(), sender);
    }
}
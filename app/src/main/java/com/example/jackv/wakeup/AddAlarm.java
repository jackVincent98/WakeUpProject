package com.example.jackv.wakeup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAlarm extends AppCompatActivity {

    TimePicker chosenTime;
    TextView alarmText;
    String alarmTime;
    String format = "";
    int hour;
    int min;
    private ArrayList<String> alarmArray = new ArrayList<>();
    private AlarmDatabaseHelper alarmDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_alarm);

        chosenTime = findViewById(R.id.alarmTimePicker);
        FloatingActionButton startAlarm = findViewById(R.id.start_alarm);
        alarmText = findViewById(R.id.alarmText);
        alarmDatabaseHelper = new AlarmDatabaseHelper(this);

        startAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.set (Calendar.HOUR_OF_DAY, chosenTime.getCurrentHour());
                cal.set (Calendar.MINUTE, chosenTime.getCurrentMinute());
                hour = cal.get(Calendar.HOUR_OF_DAY);
                min = cal.get(Calendar.MINUTE);
                setAlarm(cal);
                formatString();
                StringBuilder string = new StringBuilder().append(hour).append(":").append(min).append(" ").append(format);
                alarmTime = string.toString();
                addToAlarms();
                Intent intent = new Intent(AddAlarm.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setAlarm(Calendar targetTime){
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(AddAlarm.this, AlarmReceiver.class);
        final int id = (int) System.currentTimeMillis();
        PendingIntent appIntent = PendingIntent.getBroadcast(this, id, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetTime.getTimeInMillis(), appIntent);
    }

    // Method to format the time string
    public String formatString(){

        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        return format;
    }

    // Called when button is pressed
    public void addToAlarms(){
        Alarm alarm = new Alarm(alarmTime); // Create Alarm object using the chosen alarm time
        alarmDatabaseHelper.addAlarm(alarm); // Add alarm to databse
    }
}
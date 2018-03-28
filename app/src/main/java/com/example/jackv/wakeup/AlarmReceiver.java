package com.example.jackv.wakeup;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;
/**
 * Created by jackv on 26/03/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public final String channelID = "001";
    public final int notificationid = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

        //intent to call the activity which shows on ringing
        Intent myIntent = new Intent(context, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
        initChannel(context);
        android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentTitle("Alarm")
                .setContentText("Alarm Test")
                .setPriority(android.support.v4.app.NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationid, mBuilder.build());

        //display that alarm is ringing
        Toast.makeText(context, "Alarm Ringing.!!!", Toast.LENGTH_LONG).show();
    }

    private void initChannel(Context context){
        if (Build.VERSION.SDK_INT < 26){return;}
        CharSequence name = "Jack App";
        String description = "A notification channel for my app";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

}
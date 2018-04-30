package com.example.jackv.wakeup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    public AlarmAdapter(Context context, ArrayList<Alarm> alarms){
        super(context, 0, alarms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* Get the alarms item for this position. */
        Alarm alarm = getItem(position);
        /* Check if an existing view is being reused, otherwise inflate the view. */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_layout, parent, false);
        }
        /* Lookup views. */
        TextView display_alarm = (TextView) convertView.findViewById(R.id.display_time);
        /* Add the data to the template view. */
        display_alarm.setText(alarm.time);
        /* Return the completed view to render on screen. */
        return convertView;
    }
}
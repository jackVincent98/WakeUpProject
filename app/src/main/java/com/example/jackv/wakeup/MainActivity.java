package com.example.jackv.wakeup;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> alarmArray;
    private AlarmDatabaseHelper alarmDatabaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAlarm.class);
                startActivity(intent);
            }
        });

        list = (ListView)findViewById(R.id.listView);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                deleteContactAndRefresh(view);
            }
        });

        /* Initialise the database our SQLiteOpenHelper object*/
        alarmDatabaseHelper = new AlarmDatabaseHelper(this);

        populateList();
    }

    private void populateList(){
        /* Get contacts list from helper. */
        ArrayList<Alarm> alarms = alarmDatabaseHelper.getAlarmsList();

        /* Create a list adapter bound to the contacts list. */
        AlarmAdapter adapter = new AlarmAdapter(this, alarms);

        /* Attach the adapter to our list view. */
        list.setAdapter(adapter);
    }

    private void deleteContactAndRefresh(View view){
        /* Get text values from child views. */
        String time = ((TextView)view.findViewById(R.id.display_time)).getText().toString();

        /* Query the database. */
        int result = alarmDatabaseHelper.removeAlarm(new Alarm(time));

        /* Display toast notifying user of the number of deleted rows.  */
        Toast.makeText(MainActivity.this, result + " alarms were deleted from the database.", Toast.LENGTH_SHORT).show();

        /* Refresh the list of contacts. */
        populateList();
    }
}
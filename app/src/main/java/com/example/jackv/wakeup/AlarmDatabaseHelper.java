package com.example.jackv.wakeup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jackv.wakeup.Alarm;

import java.util.ArrayList;

public class AlarmDatabaseHelper extends SQLiteOpenHelper {
    /* Initialise constants. */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB";
    private static final String ALARMS_TABLE_NAME = "alarms";
    private static final String[] COLUMN_NAMES = {"Time"};
    /* Construct CREATE query string. */
    private static final String ALARMS_TABLE_CREATE =
            "CREATE TABLE " + ALARMS_TABLE_NAME + " (" +
                    COLUMN_NAMES[0] + " TEXT);";

    AlarmDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the database if it doesn't exist and adds the "contacts" table.
        /* Execute SQL query. */
        db.execSQL(ALARMS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // can be left empty for the purposes of this tutorial
    }

    public void addAlarm(Alarm a){
        /* Pack contact details in ContentValues object for database insertion. */
        ContentValues row = new ContentValues();
        row.put(this.COLUMN_NAMES[0], a.getTime());
        // The first parameter is a column name, the second is a value.

        /* Get writable database and insert the new row to the "contacts" table. */
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ALARMS_TABLE_NAME, null, row);
        db.close();
    }

    public ArrayList<Alarm> getAlarmsList(){
        /* Get the readable database. */
        SQLiteDatabase db = this.getReadableDatabase();

        /* Get all contacts by querying the database. */
        Cursor result = db.query(ALARMS_TABLE_NAME, COLUMN_NAMES, null, null, null, null, null, null);

        /* Convert results to a list of Contact objects. */
        ArrayList<Alarm> alarms = new ArrayList<Alarm>();

        for(int i = 0; i < result.getCount(); i++){
            result.moveToPosition(i);
            /* Create a Contact object with using data from name, email, phone columns. Add it to list. */
            alarms.add(new Alarm(result.getString(0)));
        }

        return alarms;
    }

    public int removeAlarm(Alarm a){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "Time = '" + a.time + "'";
        // Returns the number of affected rows. 0 means no rows were deleted.
        return db.delete(ALARMS_TABLE_NAME, whereClause, null);
    }
}
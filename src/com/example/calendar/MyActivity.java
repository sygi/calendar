package com.example.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.*;
import android.util.Log;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

public class MyActivity extends Activity {

    CalendarView cv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cv = (CalendarView) findViewById(R.id.calendarView);
        cv.setFirstDayOfWeek(Calendar.MONDAY);
        showCalendarsInfo();
        insertEvent();
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.d("sygi", "kliknieto" + year + " " + month + " " + dayOfMonth);
                myMethod(dayOfMonth, month, year);

            }
        });
    }

    public void showCalendarsInfo(){
        String[] EVENT_PROJECTION = new String[] {
                Calendars._ID,                           // 0
                Calendars.ACCOUNT_NAME,                  // 1
                Calendars.CALENDAR_DISPLAY_NAME,         // 2
                Calendars.OWNER_ACCOUNT                  // 3
        };

// The indices for the projection array above.
        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        int PROJECTION_DISPLAY_NAME_INDEX = 2;
        int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(Calendars.CONTENT_URI, EVENT_PROJECTION, null, null, null);
        while (c.moveToNext()){
            Log.d("sygi", "id = " + c.getLong(c.getColumnIndex(Calendars._ID)));
            Log.d("sygi", "account name = " + c.getString(PROJECTION_ACCOUNT_NAME_INDEX));
            Log.d("sygi", "display name = " + c.getString(PROJECTION_DISPLAY_NAME_INDEX));
            Log.d("sygi", "owner accout = " + c.getString(PROJECTION_OWNER_ACCOUNT_INDEX));
        }
    }
    public long getTestCalendarID(){
        Log.d("sygi", "getTestCalendarID");
        String[] EVENT_PROJECTION = new String[] {
                Calendars._ID, // 0
               // Calendars.CALENDAR_DISPLAY_NAME, // 0
        };

// The indices for the projection array above.
        int PROJECTION_ID_INDEX = 0;
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(Calendars.CONTENT_URI, EVENT_PROJECTION,
                 Calendars.CALENDAR_DISPLAY_NAME + " = ?",
                new String[] {"test"}, null);

        while (c.moveToNext()){
            Log.d("sygi", "numer = " + c.getLong(PROJECTION_ID_INDEX));
            return c.getLong(PROJECTION_ID_INDEX);
        }
        Log.e("sygi", "nie ma kalendarza ;(");
        return -1;
    }

    void creatingNewCalendar(){
        //TODO:
    }

    void insertEvent(){
        Calendar startTime = Calendar.getInstance();
        startTime.set(2013, 12, 8, 10, 30, 19);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2013, 12, 8, 11, 30, 23);
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART, startTime.getTimeInMillis());
        values.put(Events.DTEND, endTime.getTimeInMillis());
        values.put(Events.CALENDAR_ID, getTestCalendarID());
        values.put(Events.TITLE, "pu4f");
        values.put(Events.EVENT_TIMEZONE, "blabla");
        Uri uri = cr.insert(Events.CONTENT_URI, values);
        Long eventID = Long.parseLong(uri.getLastPathSegment());
        Log.d("sygi", "numer wydarzenia = " + eventID);
    }
    void myMethod(int year, int month, int dayOfMonth){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("sygi", "ok");
                dialog.dismiss();
            }
        })
                .setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("sygi", "no!");
                        dialog.cancel();
                    }
                })
                .setMessage("kliknieto " + year + " " + month + " " + dayOfMonth)
                .show();
    }

    

}

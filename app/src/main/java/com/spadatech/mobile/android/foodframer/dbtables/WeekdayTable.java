package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class WeekdayTable {

    private Weekday mWeekday;
    private DatabaseHelper mDatabaseHelper;

    public WeekdayTable(){
        mWeekday = new Weekday();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + Weekday.TABLE  + "("
                + Weekday.KEY_WEEKDAY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Weekday.KEY_WEEKDAY_NAME  + " TEXT,"
                + Weekday.KEY_WEEKDAY_PLAN_ID  + " TEXT )";
    }

    public void insert(Weekday weekday) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        ContentValues values = new ContentValues();
        values.put(Weekday.KEY_WEEKDAY_ID, weekday.getId());
        values.put(Weekday.KEY_WEEKDAY_NAME, weekday.getName());
        values.put(Weekday.KEY_WEEKDAY_PLAN_ID, weekday.getPlanId());

        // Inserting Row
        mDatabaseHelper.getDatabase().insert(Weekday.TABLE, null, values);
        mDatabaseHelper.close();
    }

    public void delete( ) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        mDatabaseHelper.getDatabase().delete(Weekday.TABLE, null, null);
        mDatabaseHelper.close();
    }

    public List<Weekday> getWeekdays(String planId){
        List<Weekday> weekdays = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + Weekday.TABLE + " Where " + Weekday.KEY_WEEKDAY_PLAN_ID + "=" + planId;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Weekday weekdayResult = new Weekday();
                weekdayResult.setId(cursor.getString(cursor.getColumnIndex(Weekday.KEY_WEEKDAY_ID)));
                weekdayResult.setName(cursor.getString(cursor.getColumnIndex(Weekday.KEY_WEEKDAY_NAME)));
                weekdayResult.setPlanId(planId);

                weekdays.add(weekdayResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        mDatabaseHelper.close();

        return weekdays;
    }

}

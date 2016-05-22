package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.PrepDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class PrepdayTable {

    private PrepDay mPrepDay;
    private DatabaseHelper mDatabaseHelper;

    public PrepdayTable(){
        mPrepDay = new PrepDay();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + PrepDay.TABLE  + "("
                + PrepDay.KEY_PREPDAY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PrepDay.KEY_PREPDAY_NAME  + " TEXT,"
                + PrepDay.KEY_PREPDAY_WEEKDAY_ID  + " TEXT )";
    }

    public void insert(PrepDay prepDay) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        ContentValues values = new ContentValues();
        values.put(PrepDay.KEY_PREPDAY_ID, prepDay.getId());
        values.put(PrepDay.KEY_PREPDAY_NAME, prepDay.getName());
        values.put(PrepDay.KEY_PREPDAY_WEEKDAY_ID, prepDay.getWeekdayId());

        // Inserting Row
        mDatabaseHelper.getDatabase().insert(PrepDay.TABLE, null, values);
        mDatabaseHelper.close();
    }

    public void delete( ) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        mDatabaseHelper.getDatabase().delete(PrepDay.TABLE, null, null);
        mDatabaseHelper.close();
    }

    public List<PrepDay> getPrepdays(String weekdayId){
        List<PrepDay> prepDays = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + PrepDay.TABLE + " Where " + PrepDay.KEY_PREPDAY_WEEKDAY_ID + "=" + weekdayId;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PrepDay prepDayResult = new PrepDay();
                prepDayResult.setId(cursor.getString(cursor.getColumnIndex(PrepDay.KEY_PREPDAY_ID)));
                prepDayResult.setName(cursor.getString(cursor.getColumnIndex(PrepDay.KEY_PREPDAY_NAME)));
                prepDayResult.setWeekdayId(weekdayId);

                prepDays.add(prepDayResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        mDatabaseHelper.close();

        return prepDays;
    }

}

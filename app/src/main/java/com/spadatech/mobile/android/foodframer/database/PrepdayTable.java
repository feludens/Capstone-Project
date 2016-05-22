package com.spadatech.mobile.android.foodframer.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spadatech.mobile.android.foodframer.managers.DatabaseManager;
import com.spadatech.mobile.android.foodframer.models.PrepDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class PrepdayTable {

    private PrepDay mPrepDay;

    public PrepdayTable(){
        mPrepDay = new PrepDay();
    }

    public static String createTable(){
        return "CREATE TABLE " + PrepDay.TABLE  + "("
                + PrepDay.KEY_PREPDAY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PrepDay.KEY_PREPDAY_NAME  + " TEXT,"
                + PrepDay.KEY_PREPDAY_WEEKDAY_ID  + " TEXT )";
    }

    public void insert(PrepDay prepDay) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(PrepDay.KEY_PREPDAY_ID, prepDay.getId());
        values.put(PrepDay.KEY_PREPDAY_NAME, prepDay.getName());
        values.put(PrepDay.KEY_PREPDAY_WEEKDAY_ID, prepDay.getWeekdayId());

        // Inserting Row
        db.insert(PrepDay.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(PrepDay.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<PrepDay> getPrepdays(String weekdayId){
        List<PrepDay> prepDays = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + PrepDay.TABLE + " Where " + PrepDay.KEY_PREPDAY_WEEKDAY_ID + "=" + weekdayId;

        Cursor cursor = db.rawQuery(query, null);
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
        DatabaseManager.getInstance().closeDatabase();

        return prepDays;
    }

}

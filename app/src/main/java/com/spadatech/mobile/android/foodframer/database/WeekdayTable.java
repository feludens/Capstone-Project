package com.spadatech.mobile.android.foodframer.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spadatech.mobile.android.foodframer.managers.DatabaseManager;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class WeekdayTable {

    private Weekday mWeekday;

    public WeekdayTable(){
        mWeekday = new Weekday();
    }

    public static String createTable(){
        return "CREATE TABLE " + Weekday.TABLE  + "("
                + Weekday.KEY_WEEKDAY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Weekday.KEY_WEEKDAY_NAME  + " TEXT,"
                + Weekday.KEY_WEEKDAY_PLAN_ID  + " TEXT )";
    }

    public void insert(Weekday weekday) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Weekday.KEY_WEEKDAY_ID, weekday.getId());
        values.put(Weekday.KEY_WEEKDAY_NAME, weekday.getName());
        values.put(Weekday.KEY_WEEKDAY_PLAN_ID, weekday.getPlanId());

        // Inserting Row
        db.insert(Weekday.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Weekday.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<Weekday> getWeekdays(String planId){
        List<Weekday> weekdays = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + Weekday.TABLE + " Where " + Weekday.KEY_WEEKDAY_PLAN_ID + "=" + planId;

        Cursor cursor = db.rawQuery(query, null);
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
        DatabaseManager.getInstance().closeDatabase();

        return weekdays;
    }

}

package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.Grocery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class GroceryTable {

    private Grocery mGrocery;
    private DatabaseHelper mDatabaseHelper;

    public GroceryTable(){
        mGrocery = new Grocery();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + Grocery.TABLE  + "("
                + Grocery.KEY_GROCERY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Grocery.KEY_GROCERY_NAME  + " TEXT,"
                + Grocery.KEY_GROCERY_WEEKDAY_ID  + " INTEGER )";
    }

    public void insert(Grocery grocery) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        ContentValues values = new ContentValues();
        values.put(Grocery.KEY_GROCERY_ID, grocery.getId());
        values.put(Grocery.KEY_GROCERY_NAME, grocery.getName());
        values.put(Grocery.KEY_GROCERY_WEEKDAY_ID, grocery.getWeekdayId());

        // Inserting Row
        mDatabaseHelper.getDatabase().insert(Grocery.TABLE, null, values);
        mDatabaseHelper.close();
    }

    public void delete( ) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        mDatabaseHelper.getDatabase().delete(Grocery.TABLE, null, null);
        mDatabaseHelper.close();
    }

    public List<Grocery> getGroceries(int weekdayId){
        List<Grocery> groceries = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + Grocery.TABLE + " Where " + Grocery.KEY_GROCERY_WEEKDAY_ID + "=" + weekdayId;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Grocery groceryResult = new Grocery();
                groceryResult.setId(cursor.getInt(cursor.getColumnIndex(Grocery.KEY_GROCERY_ID)));
                groceryResult.setName(cursor.getString(cursor.getColumnIndex(Grocery.KEY_GROCERY_NAME)));
                groceryResult.setWeekdayId(weekdayId);

                groceries.add(groceryResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        mDatabaseHelper.close();

        return groceries;
    }

}

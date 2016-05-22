package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spadatech.mobile.android.foodframer.managers.DatabaseManager;
import com.spadatech.mobile.android.foodframer.models.Grocery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class GroceryTable {

    private Grocery mGrocery;

    public GroceryTable(){
        mGrocery = new Grocery();
    }

    public static String createTable(){
        return "CREATE TABLE " + Grocery.TABLE  + "("
                + Grocery.KEY_GROCERY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Grocery.KEY_GROCERY_NAME  + " TEXT,"
                + Grocery.KEY_GROCERY_WEEKDAY_ID  + " TEXT )";
    }

    public void insert(Grocery grocery) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Grocery.KEY_GROCERY_ID, grocery.getId());
        values.put(Grocery.KEY_GROCERY_NAME, grocery.getName());
        values.put(Grocery.KEY_GROCERY_WEEKDAY_ID, grocery.getWeekdayId());

        // Inserting Row
        db.insert(Grocery.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Grocery.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<Grocery> getGroceries(String weekdayId){
        List<Grocery> groceries = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + Grocery.TABLE + " Where " + Grocery.KEY_GROCERY_WEEKDAY_ID + "=" + weekdayId;

        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Grocery groceryResult = new Grocery();
                groceryResult.setId(cursor.getString(cursor.getColumnIndex(Grocery.KEY_GROCERY_ID)));
                groceryResult.setName(cursor.getString(cursor.getColumnIndex(Grocery.KEY_GROCERY_NAME)));
                groceryResult.setWeekdayId(weekdayId);

                groceries.add(groceryResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return groceries;
    }

}

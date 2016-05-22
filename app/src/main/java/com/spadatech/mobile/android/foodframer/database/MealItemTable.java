package com.spadatech.mobile.android.foodframer.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spadatech.mobile.android.foodframer.managers.DatabaseManager;
import com.spadatech.mobile.android.foodframer.models.MealItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class MealItemTable {

    private MealItem mMealItem;

    public MealItemTable(){
        mMealItem = new MealItem();
    }

    public static String createTable(){
        return "CREATE TABLE " + MealItem.TABLE  + "("
                + MealItem.KEY_MEAL_ITEM_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MealItem.KEY_MEAL_ITEM_NAME  + " TEXT,"
                + MealItem.KEY_MEAL_ITEM_MEAL_ID  + " TEXT )";
    }

    public void insert(MealItem mealItem) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(MealItem.KEY_MEAL_ITEM_ID, mealItem.getId());
        values.put(MealItem.KEY_MEAL_ITEM_NAME, mealItem.getName());
        values.put(MealItem.KEY_MEAL_ITEM_MEAL_ID, mealItem.getMealId());

        // Inserting Row
        db.insert(MealItem.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(MealItem.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<MealItem> getMealItems(String mealId){
        List<MealItem> mealItems = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + MealItem.TABLE + " Where " + MealItem.KEY_MEAL_ITEM_MEAL_ID + "=" + mealId;

        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MealItem mealItemResult = new MealItem();
                mealItemResult.setId(cursor.getString(cursor.getColumnIndex(MealItem.KEY_MEAL_ITEM_ID)));
                mealItemResult.setName(cursor.getString(cursor.getColumnIndex(MealItem.KEY_MEAL_ITEM_NAME)));
                mealItemResult.setMealId(mealId);

                mealItems.add(mealItemResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return mealItems;
    }

}

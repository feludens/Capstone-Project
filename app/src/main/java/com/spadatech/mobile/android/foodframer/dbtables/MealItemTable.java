package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.MealItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class MealItemTable {

    private MealItem mMealItem;
    private DatabaseHelper mDatabaseHelper;

    public MealItemTable(){
        mMealItem = new MealItem();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + MealItem.TABLE  + "("
                + MealItem.KEY_MEAL_ITEM_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MealItem.KEY_MEAL_ITEM_NAME  + " TEXT,"
                + MealItem.KEY_MEAL_ITEM_MEAL_ID  + " INTEGER )";
    }

    public void insert(MealItem mealItem) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        ContentValues values = new ContentValues();
        values.put(MealItem.KEY_MEAL_ITEM_ID, mealItem.getId());
        values.put(MealItem.KEY_MEAL_ITEM_NAME, mealItem.getName());
        values.put(MealItem.KEY_MEAL_ITEM_MEAL_ID, mealItem.getMealId());

        // Inserting Row
        mDatabaseHelper.getDatabase().insert(MealItem.TABLE, null, values);
        mDatabaseHelper.close();
    }

    public void delete( ) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        mDatabaseHelper.getDatabase().delete(MealItem.TABLE, null, null);
        mDatabaseHelper.close();
    }

    public List<MealItem> getMealItems(String mealId){
        List<MealItem> mealItems = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + MealItem.TABLE + " Where " + MealItem.KEY_MEAL_ITEM_MEAL_ID + "=" + mealId;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
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
        mDatabaseHelper.close();

        return mealItems;
    }

}

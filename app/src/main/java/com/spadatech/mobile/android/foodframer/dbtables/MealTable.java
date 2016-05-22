package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class MealTable {

    private Meal mMeal;
    private DatabaseHelper mDatabaseHelper;

    public MealTable(){
        mMeal = new Meal();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + Meal.TABLE  + "("
                + Meal.KEY_MEAL_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Meal.KEY_MEAL_NAME  + " TEXT,"
                + Meal.KEY_MEAL_NOTE  + " TEXT,"
                + Meal.KEY_MEAL_WEEKDAY_ID  + " INTEGER )";
    }

    public void insert(Meal meal) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        ContentValues values = new ContentValues();
        values.put(Meal.KEY_MEAL_ID, meal.getId());
        values.put(Meal.KEY_MEAL_NAME, meal.getName());
        values.put(Meal.KEY_MEAL_NOTE, meal.getName());
        values.put(Meal.KEY_MEAL_WEEKDAY_ID, meal.getWeekdayId());

        // Inserting Row
        mDatabaseHelper.getDatabase().insert(Meal.TABLE, null, values);
        mDatabaseHelper.close();
    }

    public void delete( ) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        mDatabaseHelper.getDatabase().delete(Meal.TABLE, null, null);
        mDatabaseHelper.close();
    }

    public List<Meal> getMeals(int weekdayId){
        List<Meal> meals = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + Meal.TABLE + " Where " + Meal.KEY_MEAL_WEEKDAY_ID + "=" + weekdayId;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Meal mealResult = new Meal();
                mealResult.setId(cursor.getInt(cursor.getColumnIndex(Meal.KEY_MEAL_ID)));
                mealResult.setName(cursor.getString(cursor.getColumnIndex(Meal.KEY_MEAL_NAME)));
                mealResult.setNote(cursor.getString(cursor.getColumnIndex(Meal.KEY_MEAL_NOTE)));
                mealResult.setWeekdayId(weekdayId);

                meals.add(mealResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        mDatabaseHelper.close();

        return meals;
    }

}

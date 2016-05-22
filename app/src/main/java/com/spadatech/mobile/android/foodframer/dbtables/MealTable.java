package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spadatech.mobile.android.foodframer.managers.DatabaseManager;
import com.spadatech.mobile.android.foodframer.models.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class MealTable {

    private Meal mMeal;

    public MealTable(){
        mMeal = new Meal();
    }

    public static String createTable(){
        return "CREATE TABLE " + Meal.TABLE  + "("
                + Meal.KEY_MEAL_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Meal.KEY_MEAL_NAME  + " TEXT,"
                + Meal.KEY_MEAL_NOTE  + " TEXT,"
                + Meal.KEY_MEAL_WEEKDAY_ID  + " TEXT )";
    }

    public void insert(Meal meal) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Meal.KEY_MEAL_ID, meal.getId());
        values.put(Meal.KEY_MEAL_NAME, meal.getName());
        values.put(Meal.KEY_MEAL_NOTE, meal.getName());
        values.put(Meal.KEY_MEAL_WEEKDAY_ID, meal.getWeekdayId());

        // Inserting Row
        db.insert(Meal.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Meal.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<Meal> getMeals(String weekdayId){
        List<Meal> meals = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + Meal.TABLE + " Where " + Meal.KEY_MEAL_WEEKDAY_ID + "=" + weekdayId;

        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Meal mealResult = new Meal();
                mealResult.setId(cursor.getString(cursor.getColumnIndex(Meal.KEY_MEAL_ID)));
                mealResult.setName(cursor.getString(cursor.getColumnIndex(Meal.KEY_MEAL_NAME)));
                mealResult.setNote(cursor.getString(cursor.getColumnIndex(Meal.KEY_MEAL_NOTE)));
                mealResult.setWeekdayId(weekdayId);

                meals.add(mealResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return meals;
    }

}

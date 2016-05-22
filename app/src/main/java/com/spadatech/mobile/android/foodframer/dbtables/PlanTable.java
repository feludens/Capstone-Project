package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.managers.DatabaseManager;
import com.spadatech.mobile.android.foodframer.managers.SessionManager;
import com.spadatech.mobile.android.foodframer.models.Plan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class PlanTable {

    private Plan mPlan;

    public PlanTable(){
        mPlan = new Plan();
    }

    public static String createTable(){
        return "CREATE TABLE " + Plan.TABLE  + "("
                + Plan.KEY_PLAN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Plan.KEY_PLAN_NAME  + " TEXT,"
                + Plan.KEY_PLAN_IMAGE  + " TEXT,"
                + Plan.KEY_PLAN_USERNAME  + " TEXT )";
    }

    public void insert(Plan plan) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Plan.KEY_PLAN_ID, plan.getId());
        values.put(Plan.KEY_PLAN_NAME, plan.getName());
        values.put(Plan.KEY_PLAN_IMAGE, plan.getImage());
        values.put(Plan.KEY_PLAN_USERNAME, plan.getUsername());

        // Inserting Row
        db.insert(Plan.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Plan.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<Plan> getPlans(){
        List<Plan> plans = new ArrayList<>();
        String userName = SessionManager.get(App.getContext()).getUserInfo().get(SessionManager.KEY_USERNAME);

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + Plan.TABLE + " Where " + Plan.KEY_PLAN_USERNAME + "=" + userName;

        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Plan planResult = new Plan();
                planResult.setId(cursor.getString(cursor.getColumnIndex(Plan.KEY_PLAN_ID)));
                planResult.setName(cursor.getString(cursor.getColumnIndex(Plan.KEY_PLAN_NAME)));
                planResult.setImage(cursor.getInt(cursor.getColumnIndex(Plan.KEY_PLAN_IMAGE)));
                planResult.setUsername(userName);

                plans.add(planResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return plans;
    }

}

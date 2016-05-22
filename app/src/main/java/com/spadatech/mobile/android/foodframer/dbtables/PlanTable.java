package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.managers.SessionManager;
import com.spadatech.mobile.android.foodframer.models.Plan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class PlanTable {

    private Plan mPlan;
    private DatabaseHelper mDatabaseHelper;

    public PlanTable(){
        mPlan = new Plan();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + Plan.TABLE  + "("
                + Plan.KEY_PLAN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Plan.KEY_PLAN_NAME  + " TEXT,"
                + Plan.KEY_PLAN_IMAGE  + " TEXT,"
                + Plan.KEY_PLAN_USERNAME  + " TEXT )";
    }

    public void insert(Plan plan) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        ContentValues values = new ContentValues();
        values.put(Plan.KEY_PLAN_ID, plan.getId());
        values.put(Plan.KEY_PLAN_NAME, plan.getName());
        values.put(Plan.KEY_PLAN_IMAGE, plan.getImage());
        values.put(Plan.KEY_PLAN_USERNAME, plan.getUsername());

        // Inserting Row
        mDatabaseHelper.getDatabase().insert(Plan.TABLE, null, values);
        mDatabaseHelper.close();
    }

    public void delete( ) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        mDatabaseHelper.getDatabase().delete(Plan.TABLE, null, null);
        mDatabaseHelper.close();
    }

    public List<Plan> getPlans(){
        List<Plan> plans = new ArrayList<>();
        String userName = SessionManager.get(App.getContext()).getUserInfo().get(SessionManager.KEY_USERNAME);
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + Plan.TABLE + " Where " + Plan.KEY_PLAN_USERNAME + "=" + userName;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
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
        mDatabaseHelper.close();

        return plans;
    }

}

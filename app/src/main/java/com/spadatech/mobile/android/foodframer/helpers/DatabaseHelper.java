package com.spadatech.mobile.android.foodframer.helpers;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.MealItem;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.PrepDay;
import com.spadatech.mobile.android.foodframer.models.PrepDayItem;
import com.spadatech.mobile.android.foodframer.models.User;
import com.spadatech.mobile.android.foodframer.models.Weekday;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    // DB Version
    private static final int DATABASE_VERSION = 1;
    // DB Name
    private static final String DATABASE_NAME = "sqliteFoodFramer.db";
    // Authority
    public static final String AUTHORITY = "com.spadatech.mobile.android.foodframer.providers.userprovider";
    // Content URIs
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + User.TABLE);
    public static final Uri PLAN_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Plan.TABLE);
    public static final Uri WEEKDAY_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Weekday.TABLE);
    public static final Uri GROCERY_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Grocery.TABLE);
    public static final Uri MEAL_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Meal.TABLE);
    public static final Uri PREPDAY_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PrepDay.TABLE);
    public static final Uri GROCERY_ITEM_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + GroceryItem.TABLE);
    public static final Uri MEAL_ITEM_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MealItem.TABLE);
    public static final Uri PREPDAY_ITEM_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PrepDayItem.TABLE);
    // URI Mathcer Types
    public static final int USER = 1;
    public static final int PLAN = 2;
    public static final int WEEKDAY = 3;
    public static final int GROCERY = 4;
    public static final int MEAL = 5;
    public static final int PREPDAY = 6;
    public static final int GROCERY_ITEM = 7;
    public static final int MEAL_ITEM = 8;
    public static final int PREPDAY_ITEM = 9;

    public DatabaseHelper( ) {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

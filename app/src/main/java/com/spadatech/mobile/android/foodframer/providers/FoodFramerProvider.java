package com.spadatech.mobile.android.foodframer.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.MealItem;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.PrepDay;
import com.spadatech.mobile.android.foodframer.models.PrepDayItem;
import com.spadatech.mobile.android.foodframer.models.User;
import com.spadatech.mobile.android.foodframer.models.Weekday;

public class FoodFramerProvider extends ContentProvider {

    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    public static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(DatabaseHelper.AUTHORITY, User.TABLE, DatabaseHelper.USER);
        mUriMatcher.addURI(DatabaseHelper.AUTHORITY, Plan.TABLE, DatabaseHelper.PLAN);
        mUriMatcher.addURI(DatabaseHelper.AUTHORITY, Weekday.TABLE, DatabaseHelper.WEEKDAY);
        mUriMatcher.addURI(DatabaseHelper.AUTHORITY, Grocery.TABLE, DatabaseHelper.GROCERY);
        mUriMatcher.addURI(DatabaseHelper.AUTHORITY, Meal.TABLE, DatabaseHelper.MEAL);
        mUriMatcher.addURI(DatabaseHelper.AUTHORITY, PrepDay.TABLE, DatabaseHelper.PREPDAY);
        mUriMatcher.addURI(DatabaseHelper.AUTHORITY, GroceryItem.TABLE, DatabaseHelper.GROCERY_ITEM);
        mUriMatcher.addURI(DatabaseHelper.AUTHORITY, MealItem.TABLE, DatabaseHelper.MEAL_ITEM);
        mUriMatcher.addURI(DatabaseHelper.AUTHORITY, PrepDayItem.TABLE, DatabaseHelper.PREPDAY_ITEM);
    }

    public FoodFramerProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case DatabaseHelper.USER:
                return DatabaseHelper.USER_CONTENT_URI.toString();

            case DatabaseHelper.PLAN:
                return DatabaseHelper.PLAN_CONTENT_URI.toString();

            case DatabaseHelper.WEEKDAY:
                return DatabaseHelper.WEEKDAY_CONTENT_URI.toString();

            case DatabaseHelper.GROCERY:
                return DatabaseHelper.GROCERY_CONTENT_URI.toString();

            case DatabaseHelper.MEAL:
                return DatabaseHelper.MEAL_CONTENT_URI.toString();

            case DatabaseHelper.PREPDAY:
                return DatabaseHelper.PREPDAY_CONTENT_URI.toString();

            case DatabaseHelper.GROCERY_ITEM:
                return DatabaseHelper.GROCERY_ITEM_CONTENT_URI.toString();

            case DatabaseHelper.MEAL_ITEM:
                return DatabaseHelper.MEAL_ITEM_CONTENT_URI.toString();

            case DatabaseHelper.PREPDAY_ITEM:
                return DatabaseHelper.PREPDAY_ITEM_CONTENT_URI.toString();

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = 0;
        switch (mUriMatcher.match(uri)) {
            case DatabaseHelper.USER:
                id = mDatabase.insert(User.TABLE, null, values);
                break;
            case DatabaseHelper.PLAN:
                id = mDatabase.insert(Plan.TABLE, null, values);
                break;
            case DatabaseHelper.WEEKDAY:
                id = mDatabase.insert(Weekday.TABLE, null, values);
                break;
            case DatabaseHelper.GROCERY:
                id = mDatabase.insert(Grocery.TABLE, null, values);
                break;
            case DatabaseHelper.MEAL:
                id = mDatabase.insert(Meal.TABLE, null, values);
                break;
            case DatabaseHelper.PREPDAY:
                id = mDatabase.insert(PrepDay.TABLE, null, values);
                break;
            case DatabaseHelper.GROCERY_ITEM:
                id = mDatabase.insert(GroceryItem.TABLE, null, values);
                break;
            case DatabaseHelper.MEAL_ITEM:
                id = mDatabase.insert(MealItem.TABLE, null, values);
                break;
            case DatabaseHelper.PREPDAY_ITEM:
                id = mDatabase.insert(PrepDayItem.TABLE, null, values);
                break;
            default:
                id = 0;
        }

        if (id > 0) {
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException("Error inserting into table.");
    }

    @Override
    public boolean onCreate() {
        boolean ret = true;
        mDatabaseHelper = new DatabaseHelper(getContext());
        mDatabase = mDatabaseHelper.getWritableDatabase();

        if (mDatabase == null) {
            ret = false;
        }

        return ret;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (mUriMatcher.match(uri)) {
            case DatabaseHelper.USER:
                qb.setTables(User.TABLE);
                break;
            case DatabaseHelper.PLAN:
                qb.setTables(Plan.TABLE);
                break;
            case DatabaseHelper.WEEKDAY:
                qb.setTables(Weekday.TABLE);
                break;
            case DatabaseHelper.GROCERY:
                qb.setTables(Grocery.TABLE);
                break;
            case DatabaseHelper.MEAL:
                qb.setTables(Meal.TABLE);
                break;
            case DatabaseHelper.PREPDAY:
                qb.setTables(PrepDay.TABLE);
                break;
            case DatabaseHelper.GROCERY_ITEM:
                qb.setTables(GroceryItem.TABLE);
                break;
            case DatabaseHelper.MEAL_ITEM:
                qb.setTables(MealItem.TABLE);
                break;
            case DatabaseHelper.PREPDAY_ITEM:
                qb.setTables(PrepDayItem.TABLE);
                break;
            default:
                qb.setTables(User.TABLE);
        }

        Cursor cursor = qb.query(mDatabase, projection, selection, selectionArgs, null, null, null);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

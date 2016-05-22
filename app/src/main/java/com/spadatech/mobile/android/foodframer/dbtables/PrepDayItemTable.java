package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.PrepDayItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class PrepDayItemTable {

    private PrepDayItem mPrepDayItem;
    private DatabaseHelper mDatabaseHelper;

    public PrepDayItemTable(){
        mPrepDayItem = new PrepDayItem();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + PrepDayItem.TABLE  + "("
                + PrepDayItem.KEY_PREPDAY_ITEM_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PrepDayItem.KEY_PREPDAY_ITEM_NAME  + " TEXT,"
                + PrepDayItem.KEY_PREPDAY_ITEM_PREPDAY_ID  + " TEXT )";
    }

    public void insert(PrepDayItem prepDayItem) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        ContentValues values = new ContentValues();
        values.put(PrepDayItem.KEY_PREPDAY_ITEM_ID, prepDayItem.getId());
        values.put(PrepDayItem.KEY_PREPDAY_ITEM_NAME, prepDayItem.getName());
        values.put(PrepDayItem.KEY_PREPDAY_ITEM_PREPDAY_ID, prepDayItem.getPrepDayId());

        // Inserting Row
        mDatabaseHelper.getDatabase().insert(PrepDayItem.TABLE, null, values);
        mDatabaseHelper.close();
    }

    public void delete( ) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        mDatabaseHelper.getDatabase().delete(PrepDayItem.TABLE, null, null);
        mDatabaseHelper.close();
    }

    public List<PrepDayItem> getPrepDayItems(String prepDayId){
        List<PrepDayItem> prepDayItems = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + PrepDayItem.TABLE + " Where " + PrepDayItem.KEY_PREPDAY_ITEM_PREPDAY_ID + "=" + prepDayId;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PrepDayItem prepDayItemResult = new PrepDayItem();
                prepDayItemResult.setId(cursor.getString(cursor.getColumnIndex(PrepDayItem.KEY_PREPDAY_ITEM_ID)));
                prepDayItemResult.setName(cursor.getString(cursor.getColumnIndex(PrepDayItem.KEY_PREPDAY_ITEM_NAME)));
                prepDayItemResult.setPrepDayId(prepDayId);

                prepDayItems.add(prepDayItemResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        mDatabaseHelper.close();

        return prepDayItems;
    }

}

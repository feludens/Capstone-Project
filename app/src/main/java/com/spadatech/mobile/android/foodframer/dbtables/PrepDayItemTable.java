package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spadatech.mobile.android.foodframer.managers.DatabaseManager;
import com.spadatech.mobile.android.foodframer.models.PrepDayItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class PrepDayItemTable {

    private PrepDayItem mPrepDayItem;

    public PrepDayItemTable(){
        mPrepDayItem = new PrepDayItem();
    }

    public static String createTable(){
        return "CREATE TABLE " + PrepDayItem.TABLE  + "("
                + PrepDayItem.KEY_PREPDAY_ITEM_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PrepDayItem.KEY_PREPDAY_ITEM_NAME  + " TEXT,"
                + PrepDayItem.KEY_PREPDAY_ITEM_PREPDAY_ID  + " TEXT )";
    }

    public void insert(PrepDayItem prepDayItem) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(PrepDayItem.KEY_PREPDAY_ITEM_ID, prepDayItem.getId());
        values.put(PrepDayItem.KEY_PREPDAY_ITEM_NAME, prepDayItem.getName());
        values.put(PrepDayItem.KEY_PREPDAY_ITEM_PREPDAY_ID, prepDayItem.getPrepDayId());

        // Inserting Row
        db.insert(PrepDayItem.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(PrepDayItem.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<PrepDayItem> getPrepDayItems(String prepDayId){
        List<PrepDayItem> prepDayItems = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + PrepDayItem.TABLE + " Where " + PrepDayItem.KEY_PREPDAY_ITEM_PREPDAY_ID + "=" + prepDayId;

        Cursor cursor = db.rawQuery(query, null);
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
        DatabaseManager.getInstance().closeDatabase();

        return prepDayItems;
    }

}

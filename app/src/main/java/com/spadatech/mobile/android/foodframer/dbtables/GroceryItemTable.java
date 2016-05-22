package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class GroceryItemTable {

    private GroceryItem mGroceryItem;
    private DatabaseHelper mDatabaseHelper;

    public GroceryItemTable(){
        mGroceryItem = new GroceryItem();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + GroceryItem.TABLE  + "("
                + GroceryItem.KEY_GROCERY_ITEM_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + GroceryItem.KEY_GROCERY_ITEM_NAME  + " TEXT,"
                + GroceryItem.KEY_GROCERY_ITEM_CHECKED  + " INTEGER,"
                + GroceryItem.KEY_GROCERY_ITEM_GROCERY_ID  + " TEXT )";
    }

    public void insert(GroceryItem groceryItem) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        ContentValues values = new ContentValues();
        values.put(GroceryItem.KEY_GROCERY_ITEM_ID, groceryItem.getId());
        values.put(GroceryItem.KEY_GROCERY_ITEM_NAME, groceryItem.getName());
        values.put(GroceryItem.KEY_GROCERY_ITEM_CHECKED, groceryItem.getChecked());
        values.put(GroceryItem.KEY_GROCERY_ITEM_GROCERY_ID, groceryItem.getGroceryId());

        // Inserting Row
        mDatabaseHelper.getDatabase().insert(GroceryItem.TABLE, null, values);
        mDatabaseHelper.close();
    }

    public void delete( ) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        mDatabaseHelper.getDatabase().delete(GroceryItem.TABLE, null, null);
        mDatabaseHelper.close();
    }

    public List<GroceryItem> getGroceryItems(String groceryId){
        List<GroceryItem> groceryItems = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + GroceryItem.TABLE + " Where " + GroceryItem.KEY_GROCERY_ITEM_GROCERY_ID + "=" + groceryId;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GroceryItem groceryItemResult = new GroceryItem();
                groceryItemResult.setId(cursor.getString(cursor.getColumnIndex(GroceryItem.KEY_GROCERY_ITEM_ID)));
                groceryItemResult.setName(cursor.getString(cursor.getColumnIndex(GroceryItem.KEY_GROCERY_ITEM_NAME)));
                groceryItemResult.setChecked(cursor.getInt(cursor.getColumnIndex(GroceryItem.KEY_GROCERY_ITEM_NAME)));
                groceryItemResult.setGroceryId(groceryId);

                groceryItems.add(groceryItemResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        mDatabaseHelper.close();

        return groceryItems;
    }

}

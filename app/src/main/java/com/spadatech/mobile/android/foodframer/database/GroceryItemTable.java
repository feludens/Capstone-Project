package com.spadatech.mobile.android.foodframer.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spadatech.mobile.android.foodframer.managers.DatabaseManager;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class GroceryItemTable {

    private GroceryItem mGroceryItem;

    public GroceryItemTable(){
        mGroceryItem = new GroceryItem();
    }

    public static String createTable(){
        return "CREATE TABLE " + GroceryItem.TABLE  + "("
                + GroceryItem.KEY_GROCERY_ITEM_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + GroceryItem.KEY_GROCERY_ITEM_NAME  + " TEXT,"
                + GroceryItem.KEY_GROCERY_ITEM_CHECKED  + " INTEGER,"
                + GroceryItem.KEY_GROCERY_ITEM_GROCERY_ID  + " TEXT )";
    }

    public void insert(GroceryItem groceryItem) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(GroceryItem.KEY_GROCERY_ITEM_ID, groceryItem.getId());
        values.put(GroceryItem.KEY_GROCERY_ITEM_NAME, groceryItem.getName());
        values.put(GroceryItem.KEY_GROCERY_ITEM_CHECKED, groceryItem.getChecked());
        values.put(GroceryItem.KEY_GROCERY_ITEM_GROCERY_ID, groceryItem.getGroceryId());

        // Inserting Row
        db.insert(GroceryItem.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(GroceryItem.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<GroceryItem> getGroceryItems(String groceryId){
        List<GroceryItem> groceryItems = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + GroceryItem.TABLE + " Where " + GroceryItem.KEY_GROCERY_ITEM_GROCERY_ID + "=" + groceryId;

        Cursor cursor = db.rawQuery(query, null);
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
        DatabaseManager.getInstance().closeDatabase();

        return groceryItems;
    }

}

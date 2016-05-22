package com.spadatech.mobile.android.foodframer.models;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class GroceryItem {

    // Table name
    public static final String TABLE = "GroceryItems";

    // Table Columns
    public static final String KEY_GROCERY_ITEM_ID = "GroceryItemId";
    public static final String KEY_GROCERY_ITEM_NAME = "GrocerItemyName";
    public static final String KEY_GROCERY_ITEM_CHECKED = "GrocerItemChecked";
    public static final String KEY_GROCERY_ITEM_GROCERY_ID = "GroceryId";

    private String name;
    private int id;
    private int checked;
    private int groceryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getGroceryId() {
        return groceryId;
    }

    public void setGroceryId(int groceryId) {
        this.groceryId = groceryId;
    }
}

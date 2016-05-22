package com.spadatech.mobile.android.foodframer.models;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Grocery {

    // Table name
    public static final String TABLE = "Groceries";

    // Table Columns
    public static final String KEY_GROCERY_ID = "GroceryId";
    public static final String KEY_GROCERY_NAME = "GroceryName";
    public static final String KEY_GROCERY_WEEKDAY_ID = "WeekdayId";

    private int id;
    private String name;
    private int weekdayId;

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

    public int getWeekdayId() {
        return weekdayId;
    }

    public void setWeekdayId(int weekdayId) {
        this.weekdayId = weekdayId;
    }
}

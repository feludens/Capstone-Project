package com.spadatech.mobile.android.foodframer.models;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Meal {

    // Table name
    public static final String TABLE = "Meals";

    // Table Columns
    public static final String KEY_MEAL_ID = "MealId";
    public static final String KEY_MEAL_NAME = "MealName";
    public static final String KEY_MEAL_NOTE = "MealNotes";
    public static final String KEY_MEAL_WEEKDAY_ID = "WeekdayId";

    private String id;
    private String name;
    private String note;
    private String weekdayId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWeekdayId() {
        return weekdayId;
    }

    public void setWeekdayId(String weekdayId) {
        this.weekdayId = weekdayId;
    }
}

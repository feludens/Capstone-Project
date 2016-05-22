package com.spadatech.mobile.android.foodframer.models;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class PrepDay {

    // Table name
    public static final String TABLE = "PrepDays";

    // Table Columns
    public static final String KEY_PREPDAY_ID = "PrepDayId";
    public static final String KEY_PREPDAY_NAME = "PrepDayName";
    public static final String KEY_PREPDAY_WEEKDAY_ID = "WeekdayId";

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

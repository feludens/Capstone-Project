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

    private String id;
    private String name;
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

    public String getWeekdayId() {
        return weekdayId;
    }

    public void setWeekdayId(String weekdayId) {
        this.weekdayId = weekdayId;
    }
}

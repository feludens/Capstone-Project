package com.spadatech.mobile.android.foodframer.models;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Weekday {

    // Table name
    public static final String TABLE = "Weekdays";

    // Table Columns
    public static final String KEY_WEEKDAY_ID = "WeekdayId";
    public static final String KEY_WEEKDAY_NAME = "WeekdayName";
    public static final String KEY_WEEKDAY_ORDER = "WeekdayOrder";
    public static final String KEY_WEEKDAY_IMAGE = "WeekdayImage";
    public static final String KEY_WEEKDAY_PLAN_ID = "PlanId";

    private int id;
    private String name;
    private String planId;
    private int order;
    private int image;

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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

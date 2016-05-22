package com.spadatech.mobile.android.foodframer.models;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class PrepDayItem {

    // Table name
    public static final String TABLE = "PrepDayItems";

    // Table Columns
    public static final String KEY_PREPDAY_ITEM_ID = "PrepDayItemId";
    public static final String KEY_PREPDAY_ITEM_NAME = "PrepDayItemName";
    public static final String KEY_PREPDAY_ITEM_NOTES = "PrepDayItemNotes";
    public static final String KEY_PREPDAY_ITEM_PREPDAY_ID = "PrepDayId";

    private String id;
    private String name;
    private String notes;
    private String prepDayId;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrepDayId() {
        return prepDayId;
    }

    public void setPrepDayId(String prepDayId) {
        this.prepDayId = prepDayId;
    }
}

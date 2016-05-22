package com.spadatech.mobile.android.foodframer.models;


/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Plan {

    // Table name
    public static final String TABLE = "Plans";

    // Table Columns
    public static final String KEY_PLAN_ID = "PlanId";
    public static final String KEY_PLAN_NAME = "PlanName";
    public static final String KEY_PLAN_IMAGE = "PlanImage";
    public static final String KEY_PLAN_USERNAME = "Username";

    private String id;
    private String name;
    private String username;
    private int image;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

package com.spadatech.mobile.android.foodframer.models;

import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class MealItem extends RealmObject {
    private String name;
    private String notes;

    public MealItem() {
    }

    public MealItem(String name, String notes) {
        this.name = name;
        this.notes = notes;
    }

    public String getMealItemName() {
        return name;
    }

    public void setMealItemName(String mMealItemName) {
        this.name = mMealItemName;
    }

    public String getMealItemNotes() {
        return notes;
    }

    public void setMealItemNotes(String notes) {
        this.notes = notes;
    }
}

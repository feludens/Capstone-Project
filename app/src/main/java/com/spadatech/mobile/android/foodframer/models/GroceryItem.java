package com.spadatech.mobile.android.foodframer.models;

import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class GroceryItem extends RealmObject {
    private String mGroceryItemName;
    private boolean mIsChecked;

    public GroceryItem() {
    }

    public GroceryItem(String mGroceryItemName, boolean mIsChecked) {
        this.mGroceryItemName = mGroceryItemName;
        this.mIsChecked = mIsChecked;
    }

    public String getGroceryItemName() {
        return mGroceryItemName;
    }

    public void setGroceryItemName(String mGroceryItemName) {
        this.mGroceryItemName = mGroceryItemName;
    }

    public boolean isIsChecked() {
        return mIsChecked;
    }

    public void setIsChecked(boolean mIsChecked) {
        this.mIsChecked = mIsChecked;
    }
}

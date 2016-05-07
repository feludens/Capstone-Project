package com.spadatech.mobile.android.foodframer.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Grocery extends RealmObject {
    private String mGroceryName;
    private RealmList<GroceryItem> mGroceryItemList;

    public Grocery() {
    }

    public Grocery(String mGroceryName, RealmList<GroceryItem> mGroceryItemList) {
        this.mGroceryName = mGroceryName;
        this.mGroceryItemList = mGroceryItemList;
    }

    public String getGroceryName() {
        return mGroceryName;
    }

    public void setGroceryName(String mGroceryName) {
        this.mGroceryName = mGroceryName;
    }

    public List<GroceryItem> getmGroceryItemList() {
        return mGroceryItemList;
    }

    public void setGroceryItemList(RealmList<GroceryItem> mGroceryItemList) {
        this.mGroceryItemList = mGroceryItemList;
    }
}
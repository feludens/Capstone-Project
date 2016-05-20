package com.spadatech.mobile.android.foodframer.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Grocery extends RealmObject {
    private String mGroceryName;
    private String weekdayName;
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

    public RealmList<GroceryItem> getmGroceryItemList() {
        return mGroceryItemList;
    }

    public void setGroceryItemList(RealmList<GroceryItem> mGroceryItemList) {
        this.mGroceryItemList = mGroceryItemList;
    }

    public void addGroceryItem(GroceryItem item){
        if(mGroceryItemList != null){
            mGroceryItemList.add(item);
        }else{
            mGroceryItemList = new RealmList<>();
            mGroceryItemList.add(item);
        }
    }

    public String getWeekdayName() {
        return weekdayName;
    }

    public void setWeekdayName(String weekdayName) {
        this.weekdayName = weekdayName;
    }
}

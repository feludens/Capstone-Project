package com.spadatech.mobile.android.foodframer.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Weekday extends RealmObject{
    private String mWeekdayName;
    private RealmList<Grocery> mGroceryList;
    private RealmList<Meal> mMealList;
    private RealmList<Prep> mPrepList;

    public Weekday() {
    }

    public Weekday(String mWeekdayName, RealmList<Grocery> mGroceryList, RealmList<Meal> mMealList, RealmList<Prep> mPrepList) {
        this.mWeekdayName = mWeekdayName;
        this.mGroceryList = mGroceryList;
        this.mMealList = mMealList;
        this.mPrepList = mPrepList;
    }

    public String getWeekdayName() {
        return mWeekdayName;
    }

    public void setWeekdayName(String mWeekdayName) {
        this.mWeekdayName = mWeekdayName;
    }

    public RealmList<Grocery> getmGroceryList() {
        return mGroceryList;
    }

    public void setGroceryList(RealmList<Grocery> mGroceryList) {
        this.mGroceryList = mGroceryList;
    }

    public RealmList<Meal> getmMealList() {
        return mMealList;
    }

    public void setMealList(RealmList<Meal> mMealList) {
        this.mMealList = mMealList;
    }

    public RealmList<Prep> getmPrepList() {
        return mPrepList;
    }

    public void setPrepList(RealmList<Prep> mPrepList) {
        this.mPrepList = mPrepList;
    }
}

package com.spadatech.mobile.android.foodframer.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Meal extends RealmObject {
    private String mMealName;
    private String mMealNotes;
    private RealmList<MealItem> mMealItemList;

    public Meal() {
    }

    public Meal(String mMealName, String mMealNotes, RealmList<MealItem> mMealItemList) {
        this.mMealName = mMealName;
        this.mMealNotes = mMealNotes;
        this.mMealItemList = mMealItemList;
    }

    public String getMealName() {
        return mMealName;
    }

    public void setMealName(String mMealName) {
        this.mMealName = mMealName;
    }

    public String getMealNotes() {
        return mMealNotes;
    }

    public void setMealNotes(String mMealNotes) {
        this.mMealNotes = mMealNotes;
    }

    public RealmList<MealItem> getmMealItemList() {
        return mMealItemList;
    }

    public void setMealItemList(RealmList<MealItem> mMealItemList) {
        this.mMealItemList = mMealItemList;
    }
}

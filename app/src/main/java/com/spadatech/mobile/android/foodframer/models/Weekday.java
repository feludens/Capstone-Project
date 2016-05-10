package com.spadatech.mobile.android.foodframer.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Weekday extends RealmObject{
    private String name;
    private int image;
    private RealmList<Grocery> groceries;
    private RealmList<Meal> meals;
    private RealmList<Prep> prepdays;

    public Weekday() {
    }

    public Weekday(String name, RealmList<Grocery> groceries, RealmList<Meal> meals, RealmList<Prep> prepdays) {
        this.name = name;
        this.groceries = groceries;
        this.meals = meals;
        this.prepdays = prepdays;
    }

    public String getWeekdayName() {
        return name;
    }

    public void setWeekdayName(String mWeekdayName) {
        this.name = mWeekdayName;
    }

    public RealmList<Grocery> getGroceries() {
        return groceries;
    }

    public void setGroceryList(RealmList<Grocery> mGroceryList) {
        this.groceries = mGroceryList;
    }

    public RealmList<Meal> getMeals() {
        return meals;
    }

    public void setMealList(RealmList<Meal> mMealList) {
        this.meals = mMealList;
    }

    public RealmList<Prep> getPrepdays() {
        return prepdays;
    }

    public void setPrepList(RealmList<Prep> mPrepList) {
        this.prepdays = mPrepList;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

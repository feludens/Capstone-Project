package models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Prep extends RealmObject {
    private String mPrepName;
    private String mPrepNotes;
    private RealmList<MealItem> mMealItemsList;

    public Prep() {
    }

    public Prep(String mPrepName, String mPrepNotes, RealmList<MealItem> mMealItemsList) {
        this.mPrepName = mPrepName;
        this.mPrepNotes = mPrepNotes;
        this.mMealItemsList = mMealItemsList;
    }

    public String getPrepName() {
        return mPrepName;
    }

    public void setPrepName(String mPrepName) {
        this.mPrepName = mPrepName;
    }

    public String getPrepNotes() {
        return mPrepNotes;
    }

    public void setPrepNotes(String mPrepNotes) {
        this.mPrepNotes = mPrepNotes;
    }

    public RealmList<MealItem> getmMealItemsList() {
        return mMealItemsList;
    }

    public void setMealItemsList(RealmList<MealItem> mMealItemsList) {
        this.mMealItemsList = mMealItemsList;
    }
}

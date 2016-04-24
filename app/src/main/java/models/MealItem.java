package models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class MealItem extends RealmObject {
    private String mMealItemName;
    private RealmList<MealInstructions> mMealInstructions;

    public MealItem() {
    }

    public MealItem(String mMealItemName, RealmList<MealInstructions> mMealInstructions) {
        this.mMealItemName = mMealItemName;
        this.mMealInstructions = mMealInstructions;
    }

    public String getMealItemName() {
        return mMealItemName;
    }

    public void setMealItemName(String mMealItemName) {
        this.mMealItemName = mMealItemName;
    }

    public RealmList<MealInstructions> getmMealInstructions() {
        return mMealInstructions;
    }

    public void setMealInstructions(RealmList<MealInstructions> mMealInstructions) {
        this.mMealInstructions = mMealInstructions;
    }
}

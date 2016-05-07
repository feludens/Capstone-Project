package com.spadatech.mobile.android.foodframer.models;

import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/23/16.
 */
public class MealInstructions extends RealmObject {

    private String mInstruction;

    public String getInstruction() {
        return mInstruction;
    }

    public void setInstruction(String instruction) {
        this.mInstruction = instruction;
    }
}

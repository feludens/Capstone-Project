package com.spadatech.mobile.android.foodframer.helpers;

import com.spadatech.mobile.android.foodframer.models.Plan;

/**
 * Created by Felipe S. Pereira on 4/29/16.
 */
public class PlanHelper {

    private static PlanHelper instance;
    private Plan mPlan;

    public synchronized static PlanHelper get() {
        if (instance == null) {
            instance = new PlanHelper();
        }
        return instance;
    }

    public void setActivePlan(Plan plan){
        mPlan = plan;
    }

    public Plan getActivePlan(){
        return mPlan;
    }
}

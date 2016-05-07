package com.spadatech.mobile.android.foodframer.helpers;

import com.spadatech.mobile.android.foodframer.R;

import io.realm.Realm;
import io.realm.RealmList;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.User;

/**
 * Created by Felipe S. Pereira on 4/29/16.
 */
public class PlanHelper {

    public void addNewPlan(User user){
        if(user.getPlanList().size() == 0){
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Plan samplePlan = realm.createObject(Plan.class);
            samplePlan.setName("Sample Plan");
            samplePlan.setImage(R.drawable.google);
            samplePlan.setWeekdaysList(WeekdayHelper.newWeekdayList());
            realm.commitTransaction();

            RealmList<Plan> planList = new RealmList<>();
            planList.add(samplePlan);

            user.setPlanList(planList);
        }
    }
}

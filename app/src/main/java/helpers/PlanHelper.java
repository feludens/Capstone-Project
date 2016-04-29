package helpers;

import com.spadatech.mobile.android.foodframer.R;

import io.realm.Realm;
import models.Plan;
import models.User;

/**
 * Created by Felipe S. Pereira on 4/29/16.
 */
public class PlanHelper {

    public void addNewPlan(Realm realm, User user){
        if(user.getPlanList().size() == 0){
            realm.beginTransaction();
            Plan samplePlan = realm.createObject(Plan.class);
            samplePlan.setPlanName("Sample Plan");
            samplePlan.setImageRsc(R.drawable.google);
            samplePlan.setWeekdaysList(WeekdayHelper.newWeekdayList());
            realm.commitTransaction();
        }
    }
}

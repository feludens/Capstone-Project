package models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/13/16.
 */
public class Plan extends RealmObject{
    private String mPlanName;
    private String mPlanId;
    private int mImageRsc;
    private RealmList<Weekday> mWeekdaysList;

    public Plan() {
    }

    public Plan(String mPlanName, int mImageRsc, String mPlanId, RealmList<Weekday> mWeekdaysList) {
        this.mPlanName = mPlanName;
        this.mImageRsc = mImageRsc;
        this.mPlanId = mPlanId;
        this.mWeekdaysList = mWeekdaysList;
    }

    public String getPlanName() {
        return mPlanName;
    }

    public void setPlanName(String planName) {
        this.mPlanName = planName;
    }

    public int getImageRsc() {
        return mImageRsc;
    }

    public void setImageRsc(int imageRsc) {
        this.mImageRsc = imageRsc;
    }

    public String getPlanId() {
        return mPlanId;
    }

    public void setPlanId(String planId) {
        this.mPlanId = planId;
    }

    public RealmList<Weekday> getWeekdaysList() {
        return mWeekdaysList;
    }

    public void setWeekdaysList(RealmList<Weekday> weekdaysList) {
        this.mWeekdaysList = weekdaysList;
    }
}

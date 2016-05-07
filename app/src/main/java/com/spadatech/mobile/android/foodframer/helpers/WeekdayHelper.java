package com.spadatech.mobile.android.foodframer.helpers;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import com.spadatech.mobile.android.foodframer.models.Weekday;

/**
 * Created by Felipe S. Pereira on 4/29/16.
 */
public class WeekdayHelper {

    private static Realm mRealm;
    private static List<String> mWeekdayNameList = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    public static RealmList<Weekday> newWeekdayList() {
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        RealmList<Weekday> weekdayList = new RealmList<>();

        while (weekdayList.size() < 7){
            createNewWeekday(weekdayList.size());
        }

        mRealm.commitTransaction();
        return weekdayList;
    }

    private static void createNewWeekday(int size) {
        Weekday newWeekday = mRealm.createObject(Weekday.class);
        newWeekday.setWeekdayName(mWeekdayNameList.get(size));
//        newWeekday.setGroceryList();
//        newWeekday.setMealList();
//        newWeekday.setPrepList();
    }
}

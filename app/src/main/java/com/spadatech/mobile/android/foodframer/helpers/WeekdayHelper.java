package com.spadatech.mobile.android.foodframer.helpers;

import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Felipe S. Pereira on 4/29/16.
 */
public class WeekdayHelper {

    private static Realm mRealm;
    private static List<String> mWeekdayNameList = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    public static RealmList<Weekday> newWeekdayList(Realm realm) {
//        mRealm = Realm.getDefaultInstance();
//        mRealm.beginTransaction();
        RealmList<Weekday> weekdayList = new RealmList<>();

        while (weekdayList.size() < 7){
            weekdayList.add(createNewWeekday(realm, weekdayList.size()));
        }

//        mRealm.commitTransaction();
        return weekdayList;
    }

    private static Weekday createNewWeekday(Realm realm, int size) {
        Weekday newWeekday = realm.createObject(Weekday.class);
        newWeekday.setWeekdayName(mWeekdayNameList.get(size));
        return newWeekday;
//        newWeekday.setGroceryList();
//        newWeekday.setMealList();
//        newWeekday.setPrepList();
    }
}

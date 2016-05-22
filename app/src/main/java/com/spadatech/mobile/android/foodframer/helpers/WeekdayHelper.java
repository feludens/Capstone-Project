package com.spadatech.mobile.android.foodframer.helpers;

import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 4/29/16.
 */
public class WeekdayHelper {

    private static WeekdayHelper instance;
    private Weekday mWeekday;
    private static List<String> mWeekdayNameList = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    public synchronized static WeekdayHelper get() {
        if (instance == null) {
            instance = new WeekdayHelper();
        }
        return instance;
    }

    public Weekday getWeekday() {
        return mWeekday;
    }

    public void setWeekday(Weekday weekday) {
        this.mWeekday = weekday;
    }

    public static List<String> getWeekdayNameList() {
        return mWeekdayNameList;
    }
}

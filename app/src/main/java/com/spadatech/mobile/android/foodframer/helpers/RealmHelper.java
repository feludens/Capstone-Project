package com.spadatech.mobile.android.foodframer.helpers;

import android.content.Context;

import com.spadatech.mobile.android.foodframer.managers.SessionManager;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.MealItem;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.User;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by pereirf on 5/18/16.
 */
public class RealmHelper {

    private static RealmHelper instance;
    private static Realm mRealm;

    public synchronized static RealmHelper get() {
        if (instance == null) {
            instance = new RealmHelper();
            mRealm = Realm.getDefaultInstance();
        }
        return instance;
    }

    public User getUser(Context context){
        HashMap<String, String> mUserInfo = SessionManager.get(context).getUserInfo();
        User user = mRealm.where(User.class)
                .equalTo("username", mUserInfo.get(SessionManager.KEY_USERNAME))
                .equalTo("email", mUserInfo.get(SessionManager.KEY_EMAIL))
                .findFirst();
        return user;
    }

    public Plan getCurrentPlan(Context context) {
        User user = getUser(context);
        Plan activePlan = PlanHelper.get().getActivePlan();
        Plan plan = null;
        if(user.getPlanList() != null && !user.getPlanList().isEmpty()){
            for(int i = 0; i < user.getPlanList().size(); i++){
                if(user.getPlanList().get(i).getName().equals(activePlan.getName())){
                    plan = user.getPlanList().get(i);
                }
            }
        }

        Plan resultPlan = mRealm.where(Plan.class)
                .equalTo("name", plan.getName())
                .findFirst();

        return resultPlan;
    }

    public Weekday getCurrentWeekday(Context context) {
        Plan plan = getCurrentPlan(context);
        Weekday activeWeekday = WeekdayHelper.get().getWeekday();
        Weekday weekday = null;
        if(plan != null){
            for(int i = 0; i < plan.getWeekdaysList().size(); i++){
                if(plan.getWeekdaysList().get(i).getWeekdayName().equals(activeWeekday.getWeekdayName())){
                    weekday = plan.getWeekdaysList().get(i);
                }
            }
        }

        Weekday resultWeekday = mRealm.where(Weekday.class)
                .equalTo("name", activeWeekday.getWeekdayName())
                .equalTo("planName", plan.getName())
                .findFirst();

        return resultWeekday;
    }

    public RealmList<Meal> getCurrentWeekdayMeal(Context context) {
        Weekday weekday = getCurrentWeekday(context);
        RealmList<Meal> meals = weekday.getMeals();
        if(meals == null && meals.isEmpty()){
            meals = new RealmList<>();
            mRealm.copyToRealm(meals);
            mRealm.beginTransaction();
            weekday.setMealList(meals);
            mRealm.commitTransaction();
        }

        return weekday.getMeals();
    }

    public RealmList<MealItem> getMealItems(Context context, String mealName){
        Weekday weekday = getCurrentWeekday(context);
        RealmList<Meal> meals = weekday.getMeals();
        RealmList<MealItem> mealItems = new RealmList<>();
        for(int i = 0; i < meals.size(); i++){
            if(meals.get(i).getMealName().equals(mealName)){
                if(meals.get(i).getmMealItemList() != null && !meals.get(i).getmMealItemList().isEmpty()){
                    mealItems = meals.get(i).getmMealItemList();
                }
            }
        }
        return mealItems;
    }

    public Meal getMeal(Context context, String mealName) {
        Weekday weekday = getCurrentWeekday(context);
        Meal meal = new Meal();
        for(int i = 0; i < weekday.getMeals().size(); i++){
            if(weekday.getMeals().get(i).getMealName().equals(mealName)){
                meal = weekday.getMeals().get(i);
            }
        }
        return meal;
    }
}

package com.spadatech.mobile.android.foodframer.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.helpers.Constants;
import com.spadatech.mobile.android.foodframer.helpers.PlanHelper;
import com.spadatech.mobile.android.foodframer.helpers.RealmHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.MealItem;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.Prep;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Felipe S. Pereira on 5/12/16.
 */
public class DailyAdapter<T> extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private static final String TAG = "DailyAdapter";

    private List<T> mDataSet;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class GroceryViewHolder extends ViewHolder {
        RecyclerView recyclerView;
        TextView planName;

        public GroceryViewHolder(View v, List List) {
            super(v);
            this.planName = (TextView) v.findViewById(R.id.tv_grocery_name);
            this.recyclerView = (RecyclerView) v.findViewById(R.id.rv_groceries);
        }
    }

    public class MealViewHolder extends ViewHolder {
        RecyclerView recyclerView;
        TextView mealName;
        TextView mealNotes;

        public MealViewHolder(View v, List List) {
            super(v);
            this.mealName = (TextView) v.findViewById(R.id.cv_tv_meal_name);
            this.mealNotes = (TextView) v.findViewById(R.id.cv_tv_meal_notes);
            this.recyclerView = (RecyclerView) v.findViewById(R.id.cv_rv_meal_items);
        }
    }

    public class PrepViewHolder extends ViewHolder {
        RecyclerView recyclerView;
        TextView prepdayName;

        public PrepViewHolder(View v, List List) {
            super(v);
            this.prepdayName = (TextView) v.findViewById(R.id.cv_tv_prepday_name);
            this.recyclerView = (RecyclerView) v.findViewById(R.id.cv_rv_prepday_items);
        }
    }


    public DailyAdapter(List<T> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        View v;
        if (viewType == Constants.VIEW_TYPE_GROCERY) {
//            List<Grocery> list = mDataSet.get(0).get(Constants.VIEW_TYPE_GROCERY);
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_grocery_item, viewGroup, false);

            return new GroceryViewHolder(v, mDataSet);
        } else if (viewType == Constants.VIEW_TYPE_MEAL) {
//            List<Meal> list = mDataSet.get(0).get(Constants.VIEW_TYPE_MEAL);
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_meal_item, viewGroup, false);
            return new MealViewHolder(v, mDataSet);
        } else {
//            List<Prep> list = mDataSet.get(0).get(Constants.VIEW_TYPE_PREP);
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_prepday_item, viewGroup, false);
            return new PrepViewHolder(v, mDataSet);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String name = "";
        boolean checked = false;
        // Add logic to see if checkbox was checked or not
        // Add logic to retrieve name from realm object

        if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_GROCERY) {
            GroceryViewHolder holder = (GroceryViewHolder) viewHolder;

            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(llm);

//            Plan plan = PlanHelper.get().getActivePlan();
//            Weekday weekday = WeekdayHelper.get().getWeekday();
//
//            RealmList<GroceryItem> groceries;
//            Realm realm = Realm.getDefaultInstance();
//            realm.beginTransaction();
//
//            RealmResults<Plan> planResult = realm.where(Plan.class)
//                    .equalTo("name", plan.getName())
//                    .findAll();
//
//            RealmList<Weekday> weeekdayList = planResult.first().getWeekdaysList();
//            int index = weeekdayList.indexOf(weekday);

//            Weekday weekday = RealmHelper.get().getCurrentWeekday(mContext);
//            groceries = weekday.getGroceries().get(0).getmGroceryItemList();


            Grocery grocery = (Grocery) mDataSet.get(position);
            RealmList<GroceryItem> groceries = grocery.getmGroceryItemList();


//            realm.commitTransaction();

            GroceryItemListAdapter mAdapter = new GroceryItemListAdapter(groceries, false);
            holder.recyclerView.setAdapter(mAdapter);
        }
        else if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_MEAL) {
            MealViewHolder holder = (MealViewHolder) viewHolder;

            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(llm);

            Plan plan = PlanHelper.get().getActivePlan();
            Weekday weekday = RealmHelper.get().getCurrentWeekday(mContext);

            RealmList<MealItem> meals;
            Realm realm = Realm.getDefaultInstance();
//            realm.beginTransaction();
//
//            RealmResults<Plan> planResult = realm.where(Plan.class)
//                    .equalTo("name", plan.getName())
//                    .findAll();
//
//            RealmList<Weekday> weeekdayList = planResult.first().getWeekdaysList();
//            int index = weeekdayList.indexOf(weekday);
////
//            if(planResult.first().getWeekdaysList().get(index).getMeals() != null ||
//                    !planResult.first().getWeekdaysList().get(index).getMeals().isEmpty()){
//
//            }

            Meal meal = (Meal) mDataSet.get(position);

            holder.mealName.setText(meal.getMealName());
            holder.mealNotes.setText(meal.getMealNotes());

            meals = meal.getmMealItemList();
//            realm.commitTransaction();

            MealItemListAdapter mAdapter = new MealItemListAdapter(meals, false);
            holder.recyclerView.setAdapter(mAdapter);
        }
        else {
            PrepViewHolder holder = (PrepViewHolder) viewHolder;

            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(llm);

            Plan plan = PlanHelper.get().getActivePlan();
            Weekday weekday = WeekdayHelper.get().getWeekday();

            RealmList<MealItem> mealItems;
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            RealmResults<Plan> planResult = realm.where(Plan.class)
                    .equalTo("name", plan.getName())
                    .findAll();

            RealmList<Weekday> weeekdayList = planResult.first().getWeekdaysList();
            int index = weeekdayList.indexOf(weekday);
//
//            if(planResult.first().getWeekdaysList().get(index).getMeals() != null ||
//                    !planResult.first().getWeekdaysList().get(index).getMeals().isEmpty()){
//
//            }

            Prep prep = planResult.first().getWeekdaysList().get(index).getPrepdays().get(0);

            holder.prepdayName.setText(prep.getPrepName());

            mealItems = prep.getmMealItemsList();
            realm.commitTransaction();

            PrepMealItemListAdapter mAdapter = new PrepMealItemListAdapter(mealItems, false);
            holder.recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mDataSet.get(position).toString().contains("Grocery")){
            return Constants.VIEW_TYPE_GROCERY;
        }else if(mDataSet.get(position).toString().contains("Meal")){
            return Constants.VIEW_TYPE_MEAL;
        } else{
            return Constants.VIEW_TYPE_PREP;
        }


//        return (int) mDataSet.get(position).keySet().toArray()[0];
    }
}
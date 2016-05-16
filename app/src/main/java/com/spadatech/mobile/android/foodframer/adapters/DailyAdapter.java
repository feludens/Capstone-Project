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
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.MealItem;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.Prep;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Felipe S. Pereira on 5/12/16.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private static final String TAG = "DailyAdapter";

    private List<Map<Integer, List>> mDataSet;
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
        TextView planName;

        public MealViewHolder(View v, List List) {
            super(v);
            this.planName = (TextView) v.findViewById(R.id.tv_grocery_name);
            this.recyclerView = (RecyclerView) v.findViewById(R.id.rv_groceries);
        }
    }

    public class PrepViewHolder extends ViewHolder {
        RecyclerView recyclerView;
        TextView planName;

        public PrepViewHolder(View v, List List) {
            super(v);
            this.planName = (TextView) v.findViewById(R.id.tv_grocery_name);
            this.recyclerView = (RecyclerView) v.findViewById(R.id.rv_groceries);
        }
    }


    public DailyAdapter(List<Map<Integer, List>> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        View v;
        if (viewType == Constants.VIEW_TYPE_GROCERY) {
            List<Grocery> list = mDataSet.get(0).get(Constants.VIEW_TYPE_GROCERY);
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_grocery_item, viewGroup, false);

            return new GroceryViewHolder(v, list);
        } else if (viewType == Constants.VIEW_TYPE_MEAL) {
            List<Meal> list = mDataSet.get(0).get(Constants.VIEW_TYPE_MEAL);
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_meal_item, viewGroup, false);
            return new MealViewHolder(v, list);
        } else {
            List<Prep> list = mDataSet.get(0).get(Constants.VIEW_TYPE_PREP);
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_grocery_item, viewGroup, false);
            return new PrepViewHolder(v, list);
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

            Plan plan = PlanHelper.get().getActivePlan();
            Weekday weekday = WeekdayHelper.get().getWeekday();

            RealmList<GroceryItem> groceries;
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            RealmResults<Plan> planResult = realm.where(Plan.class)
                    .equalTo("name", plan.getName())
                    .findAll();

            RealmList<Weekday> weeekdayList = planResult.first().getWeekdaysList();
            int index = weeekdayList.indexOf(weekday);

            groceries = planResult.first().getWeekdaysList().get(index).getGroceries().get(0).getmGroceryItemList();
            realm.commitTransaction();

            GroceryItemListAdapter mAdapter = new GroceryItemListAdapter(groceries, false);
            holder.recyclerView.setAdapter(mAdapter);
        }
        else if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_MEAL) {
            MealViewHolder holder = (MealViewHolder) viewHolder;

            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(llm);

            Plan plan = PlanHelper.get().getActivePlan();
            Weekday weekday = WeekdayHelper.get().getWeekday();

            RealmList<MealItem> meals;
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

            meals = planResult.first().getWeekdaysList().get(index).getMeals().get(0).getmMealItemList();
            realm.commitTransaction();

            GroceryItemListAdapter mAdapter = new GroceryItemListAdapter(meals, false);
            holder.recyclerView.setAdapter(mAdapter);
        }
        else {
            PrepViewHolder holder = (PrepViewHolder) viewHolder;
//            holder.checkBox.setChecked(checked);
//            holder.checkBox.setText(name);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (int) mDataSet.get(position).keySet().toArray()[0];
    }
}
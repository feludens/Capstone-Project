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
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.MealItem;
import com.spadatech.mobile.android.foodframer.models.Prep;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

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
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_grocery_item, viewGroup, false);

            return new GroceryViewHolder(v, mDataSet);
        } else if (viewType == Constants.VIEW_TYPE_MEAL) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_meal_item, viewGroup, false);
            return new MealViewHolder(v, mDataSet);
        } else {
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

            Grocery grocery = (Grocery) mDataSet.get(position);
            RealmList<GroceryItem> groceries = grocery.getmGroceryItemList();

            GroceryItemListAdapter mAdapter = new GroceryItemListAdapter(groceries, false);
            holder.recyclerView.setAdapter(mAdapter);
        }
        else if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_MEAL) {
            MealViewHolder holder = (MealViewHolder) viewHolder;

            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(llm);

            RealmList<MealItem> meals;
            Meal meal = (Meal) mDataSet.get(position);

            holder.mealName.setText(meal.getMealName());
            holder.mealNotes.setText(meal.getMealNotes());

            meals = meal.getmMealItemList();

            MealItemListAdapter mAdapter = new MealItemListAdapter(meals, false);
            holder.recyclerView.setAdapter(mAdapter);
        } else {
            PrepViewHolder holder = (PrepViewHolder) viewHolder;

            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(llm);

            Prep prep = (Prep) mDataSet.get(position);
            holder.prepdayName.setText(prep.getPrepName());

            RealmList<MealItem> mealItems = prep.getmMealItemsList();

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
        if(mDataSet.get(position) instanceof Grocery){
            return Constants.VIEW_TYPE_GROCERY;
        }else if(mDataSet.get(position) instanceof Meal){
            return Constants.VIEW_TYPE_MEAL;
        } else{
            return Constants.VIEW_TYPE_PREP;
        }
    }

    public void swap(List<T> newDataSet){
        Realm realm = Realm.getDefaultInstance();
        if (mDataSet != null) {
            mDataSet.clear();
            mDataSet.addAll(newDataSet);
        }
        else {
            mDataSet = newDataSet;
        }
        notifyDataSetChanged();
    }
}
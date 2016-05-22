package com.spadatech.mobile.android.foodframer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.helpers.Constants;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.MealItem;
import com.spadatech.mobile.android.foodframer.models.PrepDay;
import com.spadatech.mobile.android.foodframer.models.PrepDayItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/12/16.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private static final String TAG = "DailyAdapter";

    private Cursor mCursor;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class GroceryViewHolder extends ViewHolder {
        RecyclerView recyclerView;
        TextView groceryListName;

        public GroceryViewHolder(View v) {
            super(v);
            this.groceryListName = (TextView) v.findViewById(R.id.tv_grocery_name);
            this.recyclerView = (RecyclerView) v.findViewById(R.id.rv_groceries);
        }
    }

    public class MealViewHolder extends ViewHolder {
        RecyclerView recyclerView;
        TextView mealName;
        TextView mealNotes;

        public MealViewHolder(View v) {
            super(v);
            this.mealName = (TextView) v.findViewById(R.id.cv_tv_meal_name);
            this.mealNotes = (TextView) v.findViewById(R.id.cv_tv_meal_notes);
            this.recyclerView = (RecyclerView) v.findViewById(R.id.cv_rv_meal_items);
        }
    }

    public class PrepViewHolder extends ViewHolder {
        RecyclerView recyclerView;
        TextView prepdayName;

        public PrepViewHolder(View v) {
            super(v);
            this.prepdayName = (TextView) v.findViewById(R.id.cv_tv_prepday_name);
            this.recyclerView = (RecyclerView) v.findViewById(R.id.cv_rv_prepday_items);
        }
    }


    public DailyAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == Constants.VIEW_TYPE_GROCERY) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_grocery_item, viewGroup, false);

            return new GroceryViewHolder(v);
        } else if (viewType == Constants.VIEW_TYPE_MEAL) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_meal_item, viewGroup, false);
            return new MealViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_prepday_item, viewGroup, false);
            return new PrepViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_GROCERY) {
            GroceryViewHolder holder = (GroceryViewHolder) viewHolder;

            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(llm);
            holder.groceryListName.setText(holder.groceryListName.getText().toString().toUpperCase());

            mCursor.moveToPosition(position);
            int groceryId = mCursor.getInt(mCursor.getColumnIndex(Grocery.KEY_GROCERY_ID));

            Uri uri = DatabaseHelper.GROCERY_ITEM_CONTENT_URI;
            String whereClause = "GroceryId = " + groceryId;
            Cursor cursor = mContext.getContentResolver().query(uri, null, whereClause, null, null);
            List<GroceryItem> mGroceryItemList = new ArrayList<>();

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        GroceryItem item = new GroceryItem();
                        item.setId(cursor.getInt(cursor.getColumnIndex(GroceryItem.KEY_GROCERY_ITEM_ID)));
                        item.setName(cursor.getString(cursor.getColumnIndex(GroceryItem.KEY_GROCERY_ITEM_NAME)));
                        item.setChecked(cursor.getInt(cursor.getColumnIndex(GroceryItem.KEY_GROCERY_ITEM_CHECKED)));
                        item.setGroceryId(groceryId);
                        mGroceryItemList.add(item);
                    }
                }
            }

            GroceryItemListAdapter mAdapter = new GroceryItemListAdapter(mGroceryItemList, false);
            holder.recyclerView.setAdapter(mAdapter);
        }
        else if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_MEAL) {
            MealViewHolder holder = (MealViewHolder) viewHolder;

            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(llm);

            mCursor.moveToPosition(position);
            holder.mealName.setText(mCursor.getString(mCursor.getColumnIndex(Meal.KEY_MEAL_NAME)));
            holder.mealNotes.setText(mCursor.getString(mCursor.getColumnIndex(Meal.KEY_MEAL_NOTE)));
            int mealId = mCursor.getInt(mCursor.getColumnIndex(Meal.KEY_MEAL_ID));

            Uri uri = DatabaseHelper.MEAL_ITEM_CONTENT_URI;
            String whereClause = "MealId = " + mealId;
            Cursor cursor = mContext.getContentResolver().query(uri, null, whereClause, null, null);
            List<MealItem> mMealItemList = new ArrayList<>();

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        MealItem item = new MealItem();
                        item.setId(cursor.getInt(cursor.getColumnIndex(MealItem.KEY_MEAL_ITEM_ID)));
                        item.setName(cursor.getString(cursor.getColumnIndex(MealItem.KEY_MEAL_ITEM_NAME)));
                        item.setMealId(mealId);
                        mMealItemList.add(item);
                    }
                }
            }

            MealItemListAdapter mAdapter = new MealItemListAdapter(mMealItemList, false);
            holder.recyclerView.setAdapter(mAdapter);
        } else {
            PrepViewHolder holder = (PrepViewHolder) viewHolder;

            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(llm);

            mCursor.moveToPosition(position);
            holder.prepdayName.setText(mCursor.getString(mCursor.getColumnIndex(PrepDay.KEY_PREPDAY_NAME)));
            int prepdayId = mCursor.getInt(mCursor.getColumnIndex(PrepDay.KEY_PREPDAY_ID));

            Uri uri = DatabaseHelper.PREPDAY_ITEM_CONTENT_URI;
            String whereClause = "PrepDayId = " + prepdayId;
            Cursor cursor = mContext.getContentResolver().query(uri, null, whereClause, null, null);
            List<PrepDayItem> mPreDayItemList = new ArrayList<>();

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        PrepDayItem item = new PrepDayItem();
                        item.setId(cursor.getInt(cursor.getColumnIndex(PrepDayItem.KEY_PREPDAY_ITEM_ID)));
                        item.setName(cursor.getString(cursor.getColumnIndex(PrepDayItem.KEY_PREPDAY_ITEM_NAME)));
                        item.setNotes(cursor.getString(cursor.getColumnIndex(PrepDayItem.KEY_PREPDAY_ITEM_NOTES)));
                        item.setPrepDayId(prepdayId);
                        mPreDayItemList.add(item);
                    }
                }
            }

            PrepdayItemListAdapter mAdapter = new PrepdayItemListAdapter(mPreDayItemList, false);
            holder.recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(mCursor.getColumnName(0).contains("Grocery")){
            return Constants.VIEW_TYPE_GROCERY;
        }else if(mCursor.getColumnName(0).contains("Meal")){
            return Constants.VIEW_TYPE_MEAL;
        }else{
            return Constants.VIEW_TYPE_PREP;
        }

//        if(mCursor.get(position) instanceof Grocery){
//            return Constants.VIEW_TYPE_GROCERY;
//        }else if(mCursor.get(position) instanceof Meal){
//            return Constants.VIEW_TYPE_MEAL;
//        } else{
//            return Constants.VIEW_TYPE_PREP;
//        }
    }
}
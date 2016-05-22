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
        String name = "";
        boolean checked = false;
        // Add logic to see if checkbox was checked or not
        // Add logic to retrieve name from realm object

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
//        else if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_MEAL) {
//            MealViewHolder holder = (MealViewHolder) viewHolder;
//
//            LinearLayoutManager llm = new LinearLayoutManager(mContext);
//            holder.recyclerView.setLayoutManager(llm);
//
//            RealmList<MealItem> meals;
//            Meal meal = (Meal) mCursor.get(position);
//
//            holder.mealName.setText(meal.getMealName().toUpperCase());
//            holder.mealNotes.setText(meal.getMealNotes());
//
//            meals = meal.getmMealItemList();
//
//            MealItemListAdapter mAdapter = new MealItemListAdapter(meals, false);
//            holder.recyclerView.setAdapter(mAdapter);
//        } else {
//            PrepViewHolder holder = (PrepViewHolder) viewHolder;
//
//            LinearLayoutManager llm = new LinearLayoutManager(mContext);
//            holder.recyclerView.setLayoutManager(llm);
//
//            PrepDay prep = (PrepDay) mCursor.get(position);
//            holder.prepdayName.setText(prep.getPrepName().toUpperCase());
//
//            RealmList<MealItem> mealItems = prep.getmMealItemsList();
//
//            PrepMealItemListAdapter mAdapter = new PrepMealItemListAdapter(mealItems, false);
//            holder.recyclerView.setAdapter(mAdapter);
//        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        mCursor.getType(position);
        return 0;

//        if(mCursor.get(position) instanceof Grocery){
//            return Constants.VIEW_TYPE_GROCERY;
//        }else if(mCursor.get(position) instanceof Meal){
//            return Constants.VIEW_TYPE_MEAL;
//        } else{
//            return Constants.VIEW_TYPE_PREP;
//        }
    }
}
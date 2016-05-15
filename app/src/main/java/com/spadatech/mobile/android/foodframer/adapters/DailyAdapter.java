package com.spadatech.mobile.android.foodframer.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.helpers.Constants;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;

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
        ListView lv;
        CheckBox checkBox;
        String name = "";
        boolean checked = false;
        RecyclerView recyclerView;
        TextView planName;
        // Add logic to see if checkbox was checked or not
        // Add logic to retrieve name from realm object

        public GroceryViewHolder(View v, List List) {
            super(v);
//            this.checkBox = (CheckBox) v.findViewById(R.id.lv_checkbox);
            this.planName = (TextView) v.findViewById(R.id.tv_grocery_name);
            recyclerView = (RecyclerView) v.findViewById(R.id.rv_groceries);
//            checkBox.setChecked(checked);
//            checkBox.setText(name);
        }
    }

    public class MealViewHolder extends ViewHolder {
//        CheckBox checkBox;

        public MealViewHolder(View v, List List) {
            super(v);
//            this.checkBox = (CheckBox) v.findViewById(R.id.lv_checkbox);
        }
    }

    public class PrepViewHolder extends ViewHolder {
//        CheckBox checkBox;
//        String name = "";
//        boolean checked = false;

        public PrepViewHolder(View v, List List) {
            super(v);
//            this.checkBox = (CheckBox) v.findViewById(R.id.lv_checkbox);
//            checkBox.setChecked(checked);
//            checkBox.setText(name);
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
            List<Grocery> list = mDataSet.get(0).get(Constants.VIEW_TYPE_MEAL);
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_grocery_item, viewGroup, false);
            return new MealViewHolder(v, list);
        } else {
            List<Grocery> list = mDataSet.get(0).get(Constants.VIEW_TYPE_PREP);
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

            RealmList<GroceryItem> groceries;
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<Grocery> groceryResult = realm.where(Grocery.class)
                    .equalTo("mGroceryName", "Grocery List")
                    .findAll();
            groceries = groceryResult.first().getmGroceryItemList();
            realm.commitTransaction();

            //This is currently sending a list of groceries. Must send a list of groceries item
            GroceryItemListAdapter mAdapter = new GroceryItemListAdapter(groceries, false);
            holder.recyclerView.setAdapter(mAdapter);
//            holder.checkBox.setChecked(checked);
//            holder.checkBox.setText(name);
        }
        else if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_MEAL) {
            MealViewHolder holder = (MealViewHolder) viewHolder;
//            holder.checkBox.setChecked(checked);
//            holder.checkBox.setText(name);
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
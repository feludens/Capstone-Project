package com.spadatech.mobile.android.foodframer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.helpers.Constants;
import com.spadatech.mobile.android.foodframer.models.Grocery;

import java.util.List;
import java.util.Map;

/**
 * Created by Felipe S. Pereira on 5/12/16.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private static final String TAG = "DailyAdapter";

    private List<Map<Integer, List>> mDataSet;

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
        // Add logic to see if checkbox was checked or not
        // Add logic to retrieve name from realm object

        public GroceryViewHolder(View v, List List) {
            super(v);
            this.checkBox = (CheckBox) v.findViewById(R.id.lv_checkbox);
            this.lv = (ListView) v.findViewById(R.id.lv_groceries);
            checkBox.setChecked(checked);
            checkBox.setText(name);
        }
    }

    public class MealViewHolder extends ViewHolder {
        CheckBox checkBox;

        public MealViewHolder(View v, List List) {
            super(v);
            this.checkBox = (CheckBox) v.findViewById(R.id.lv_checkbox);
        }
    }

    public class PrepViewHolder extends ViewHolder {
        CheckBox checkBox;
        String name = "";
        boolean checked = false;

        public PrepViewHolder(View v, List List) {
            super(v);
            this.checkBox = (CheckBox) v.findViewById(R.id.lv_checkbox);
            checkBox.setChecked(checked);
            checkBox.setText(name);
        }
    }


    public DailyAdapter(List<Map<Integer, List>> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
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
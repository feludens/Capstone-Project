package com.spadatech.mobile.android.foodframer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;

import java.util.List;

/**
 * Created by pereirf on 5/13/16.
 */
public class CheckboxListAdapter extends RecyclerView.Adapter<CheckboxListAdapter.ViewHolder> {
    private static final String TAG = "DailyAdapter";

    private List mList;
    private boolean mIsEditMode = false;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class GroceryItemViewHolder extends ViewHolder {
        CheckBox checkBox;
        ImageButton deleteButton;
//        boolean checked = false;
        // Add logic to see if checkbox was checked or not
        // Add logic to retrieve name from realm object

        public GroceryItemViewHolder(View v) {
            super(v);
            this.checkBox = (CheckBox) v.findViewById(R.id.checkbox_item);
            this.deleteButton = (ImageButton) v.findViewById(R.id.ib_delete);
//            checkBox.setChecked(checked);
//            checkBox.setText(name);
        }
    }

//    public class MealViewHolder extends ViewHolder {
//        CheckBox checkBox;
//
//        public MealViewHolder(View v, List List) {
//            super(v);
//            this.checkBox = (CheckBox) v.findViewById(R.id.lv_checkbox);
//        }
//    }
//
//    public class PrepViewHolder extends ViewHolder {
//        CheckBox checkBox;
//        String name = "";
//        boolean checked = false;
//
//        public PrepViewHolder(View v, List List) {
//            super(v);
//            this.checkBox = (CheckBox) v.findViewById(R.id.lv_checkbox);
//            checkBox.setChecked(checked);
//            checkBox.setText(name);
//        }
//    }


    public CheckboxListAdapter(List list, boolean editMode) {
        mList = list;
        mIsEditMode = editMode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.listview_checkbox_item, viewGroup, false);

        return new GroceryItemViewHolder(v);
//        if (viewType == Constants.VIEW_TYPE_GROCERY) {
//            List<Grocery> list = mDataSet.get(0).get(Constants.VIEW_TYPE_GROCERY);
//            v = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.cardview_grocery_item, viewGroup, false);
//
//            return new GroceryViewHolder(v, list);
//        } else if (viewType == Constants.VIEW_TYPE_MEAL) {
//            List<Grocery> list = mDataSet.get(0).get(Constants.VIEW_TYPE_MEAL);
//            v = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.cardview_grocery_item, viewGroup, false);
//            return new MealViewHolder(v, list);
//        } else {
//            List<Grocery> list = mDataSet.get(0).get(Constants.VIEW_TYPE_PREP);
//            v = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.cardview_grocery_item, viewGroup, false);
//            return new PrepViewHolder(v, list);
//        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        GroceryItem grocery = (GroceryItem) mList.get(position);
        String name = grocery.getGroceryItemName();
        boolean checked = grocery.isIsChecked();
        // Add logic to see if checkbox was checked or not
        // Add logic to retrieve name from realm object

//        if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_GROCERY) {
        GroceryItemViewHolder holder = (GroceryItemViewHolder) viewHolder;
            holder.checkBox.setChecked(checked);
            holder.checkBox.setText(name);

        if(mIsEditMode){
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
//        }
//        else if (viewHolder.getItemViewType() == Constants.VIEW_TYPE_MEAL) {
//            MealViewHolder holder = (MealViewHolder) viewHolder;
//            holder.checkBox.setChecked(checked);
//            holder.checkBox.setText(name);
//        }
//        else {
//            PrepViewHolder holder = (PrepViewHolder) viewHolder;
//            holder.checkBox.setChecked(checked);
//            holder.checkBox.setText(name);
//        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        return (int) mDataSet.get(position).keySet().toArray()[0];
//    }
}
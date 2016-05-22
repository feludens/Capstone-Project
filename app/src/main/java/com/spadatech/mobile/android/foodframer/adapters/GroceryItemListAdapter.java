package com.spadatech.mobile.android.foodframer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Felipe S. Pereira on 5/13/16.
 */
public class GroceryItemListAdapter extends RecyclerView.Adapter<GroceryItemListAdapter.ViewHolder> {
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

        public GroceryItemViewHolder(View v) {
            super(v);
            this.checkBox = (CheckBox) v.findViewById(R.id.checkbox_item);
            this.deleteButton = (ImageButton) v.findViewById(R.id.ib_delete);
        }
    }

    public GroceryItemListAdapter(List list, boolean editMode) {
        mList = list;
        mIsEditMode = editMode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listview_checkbox_item, viewGroup, false);

        return new GroceryItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        GroceryItem grocery = (GroceryItem) mList.get(position);
//        String name = grocery.getGroceryItemName();
//        boolean checked = grocery.isIsChecked();

        GroceryItemViewHolder holder = (GroceryItemViewHolder) viewHolder;
//        holder.checkBox.setChecked(checked);
//        holder.checkBox.setText(name);

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

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                GroceryItem grocery = (GroceryItem) mList.get(position);
//                grocery.setIsChecked(isChecked);
                realm.commitTransaction();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
package com.spadatech.mobile.android.foodframer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.models.MealItem;

import java.util.List;

/**
 * Created by pereirf on 5/13/16.
 */
public class PrepdayItemListAdapter extends RecyclerView.Adapter<PrepdayItemListAdapter.ViewHolder> {
    private static final String TAG = "PrepdayItemListAdapter";

    private List mList;
    private boolean mIsEditMode = false;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class PrepdayViewHolder extends ViewHolder {
        TextView name;
        TextView note;
        ImageButton deleteButton;

        public PrepdayViewHolder(View v) {
            super(v);
            this.name = (TextView) v.findViewById(R.id.tv_item_name);
            this.name = (TextView) v.findViewById(R.id.tv_item_note);
            this.deleteButton = (ImageButton) v.findViewById(R.id.ib_delete);
        }
    }

    public PrepdayItemListAdapter(List list, boolean editMode) {
        mList = list;
        mIsEditMode = editMode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listview_prepday_item, viewGroup, false);

        return new PrepdayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        MealItem mealItem = (MealItem) mList.get(position);
        String name = mealItem.getMealItemName();
        String note = mealItem.getMealItemNotes();

        PrepdayViewHolder holder = (PrepdayViewHolder) viewHolder;
        holder.name.setText(name);
        holder.note.setText(note);

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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
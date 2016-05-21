package com.spadatech.mobile.android.foodframer.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
public class PrepMealItemListAdapter extends RecyclerView.Adapter<PrepMealItemListAdapter.ViewHolder> {
    private static final String TAG = "PrepMealItemListAdapter";

    private List mList;
    private boolean mIsEditMode = false;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class MealViewHolder extends ViewHolder {
        TextView name;
        TextView note;
        ImageButton deleteButton;

        public MealViewHolder(View v) {
            super(v);
            this.name = (TextView) v.findViewById(R.id.tv_item_name);
            this.note = (TextView) v.findViewById(R.id.tv_item_note);
            this.deleteButton = (ImageButton) v.findViewById(R.id.ib_delete);
        }
    }

    public PrepMealItemListAdapter(List list, boolean editMode) {
        mList = list;
        mIsEditMode = editMode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listview_prepday_item, viewGroup, false);

        return new MealViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        MealItem mealItem = (MealItem) mList.get(position);
        String name = mealItem.getMealItemName();
        String note = mealItem.getMealItemNotes();

        MealViewHolder holder = (MealViewHolder) viewHolder;
        holder.name.setText(Html.fromHtml("<u>" + name + "</u>"));
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
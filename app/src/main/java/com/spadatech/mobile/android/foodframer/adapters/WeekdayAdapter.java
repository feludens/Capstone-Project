package com.spadatech.mobile.android.foodframer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.models.Weekday;
import com.spadatech.mobile.android.foodframer.viewholders.WeekdayViewHolder;

import java.util.List;

/**
 * Created by Felipe S. Pereira on 4/14/16.
 */
public class WeekdayAdapter extends RecyclerView.Adapter<WeekdayViewHolder> implements WeekdayViewHolder.OnItemClickListener {

    private List<Weekday> mWeekdayList;
    private OnWeekdayClickListener mListener;

    public WeekdayAdapter(List<Weekday> weekdayList, OnWeekdayClickListener listener){
        this.mWeekdayList = weekdayList;
        this.mListener = listener;
    }

    @Override
    public WeekdayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_weekday_item, parent, false);
        WeekdayViewHolder wvh = new WeekdayViewHolder(view, this);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WeekdayViewHolder holder, int position) {
        holder.dayName.setText(mWeekdayList.get(position).getWeekdayName());
//        holder.dayImage.setImageResource(mWeekdayList.get(position).getWeekdayName());
    }

    @Override
    public int getItemCount() {
        return mWeekdayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClicked(int position) {
        mListener.onWeekdayClicked(mWeekdayList.get(position));
    }

    public interface OnWeekdayClickListener {
        void onWeekdayClicked(Weekday weekday);
    }

}
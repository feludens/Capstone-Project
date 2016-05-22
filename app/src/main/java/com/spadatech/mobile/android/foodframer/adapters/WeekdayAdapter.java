package com.spadatech.mobile.android.foodframer.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.models.Weekday;
import com.spadatech.mobile.android.foodframer.viewholders.WeekdayViewHolder;

/**
 * Created by Felipe S. Pereira on 4/14/16.
 */
public class WeekdayAdapter extends RecyclerView.Adapter<WeekdayViewHolder> implements WeekdayViewHolder.OnItemClickListener {

    private Cursor mWeekdayCursor;
    private OnWeekdayClickListener mListener;

    public WeekdayAdapter(OnWeekdayClickListener listener, Cursor cursor){
        this.mWeekdayCursor = cursor;
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
        mWeekdayCursor.moveToPosition(position);
        holder.dayName.setText(mWeekdayCursor.getString(mWeekdayCursor.getColumnIndex(Weekday.KEY_WEEKDAY_NAME)));
        holder.dayImage.setImageResource(mWeekdayCursor.getInt(mWeekdayCursor.getColumnIndex(Weekday.KEY_WEEKDAY_IMAGE)));
    }

    @Override
    public int getItemCount() {
        return mWeekdayCursor.getCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClicked(int position) {
        Weekday selectedWeekday = new Weekday();
        selectedWeekday.setId(mWeekdayCursor.getString(mWeekdayCursor.getColumnIndex(Weekday.KEY_WEEKDAY_ID)));
        selectedWeekday.setName(mWeekdayCursor.getString(mWeekdayCursor.getColumnIndex(Weekday.KEY_WEEKDAY_NAME)));
        selectedWeekday.setImage(mWeekdayCursor.getInt(mWeekdayCursor.getColumnIndex(Weekday.KEY_WEEKDAY_IMAGE)));
        selectedWeekday.setOrder(mWeekdayCursor.getInt(mWeekdayCursor.getColumnIndex(Weekday.KEY_WEEKDAY_ORDER)));
        selectedWeekday.setPlanId(mWeekdayCursor.getString(mWeekdayCursor.getColumnIndex(Weekday.KEY_WEEKDAY_PLAN_ID)));

        mListener.onWeekdayClicked(selectedWeekday);
    }

    public interface OnWeekdayClickListener {
        void onWeekdayClicked(Weekday weekday);
    }

}
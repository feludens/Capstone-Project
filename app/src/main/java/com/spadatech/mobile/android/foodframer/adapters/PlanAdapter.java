package com.spadatech.mobile.android.foodframer.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.viewholders.PlanViewHolder;

/**
 * Created by Felipe S. Pereira on 4/14/16.
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanViewHolder> implements PlanViewHolder.OnItemClickListener {

    private Cursor mPlanCursor;
    private OnPlanClickListener mListener;

    public PlanAdapter(OnPlanClickListener listener, Cursor cursor){
        this.mPlanCursor = cursor;
        this.mListener = listener;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_plan_item, parent, false);
        PlanViewHolder pvh = new PlanViewHolder(view, this);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        mPlanCursor.moveToPosition(position);

        holder.planName.setText(mPlanCursor.getString(mPlanCursor.getColumnIndex(Plan.KEY_PLAN_NAME)));
        holder.planImage.setImageResource(mPlanCursor.getInt(mPlanCursor.getColumnIndex(Plan.KEY_PLAN_IMAGE)));
    }

    @Override
    public int getItemCount() {
        return mPlanCursor.getCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClicked(int position) {
        Plan selectedPlan = new Plan();
        selectedPlan.setId(mPlanCursor.getInt(mPlanCursor.getColumnIndex(Plan.KEY_PLAN_ID)));
        selectedPlan.setName(mPlanCursor.getString(mPlanCursor.getColumnIndex(Plan.KEY_PLAN_NAME)));
        selectedPlan.setImage(mPlanCursor.getInt(mPlanCursor.getColumnIndex(Plan.KEY_PLAN_IMAGE)));
        selectedPlan.setUsername(mPlanCursor.getString(mPlanCursor.getColumnIndex(Plan.KEY_PLAN_USERNAME)));

        mListener.onPlanClicked(selectedPlan);
    }

    public interface OnPlanClickListener {
        void onPlanClicked(Plan plan);
    }

}
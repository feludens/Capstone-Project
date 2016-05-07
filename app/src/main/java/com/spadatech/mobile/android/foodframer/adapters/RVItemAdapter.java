package com.spadatech.mobile.android.foodframer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.viewholders.PlanViewHolder;

import java.util.List;

/**
 * Created by Felipe S. Pereira on 4/14/16.
 */
public class RVItemAdapter extends RecyclerView.Adapter<PlanViewHolder> implements PlanViewHolder.OnItemClickListener {

    private List<Plan> mPlanList;
    private OnPlanClickListener mListener;

    public RVItemAdapter(List<Plan> planList, OnPlanClickListener listener){
        this.mPlanList = planList;
        this.mListener = listener;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        PlanViewHolder pvh = new PlanViewHolder(view, this);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        holder.planName.setText(mPlanList.get(position).getName());
        holder.planImage.setImageResource(mPlanList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mPlanList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClicked(int position) {
        mListener.onPlanClicked(mPlanList.get(position));
    }

    public interface OnPlanClickListener {
        void onPlanClicked(Plan plan);
    }

}
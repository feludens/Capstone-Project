package com.spadatech.mobile.android.foodframer.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spadatech.mobile.android.foodframer.R;

/**
 * Created by Felipe S. Pereira on 5/6/16.
 */
public class PlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    CardView cardView;
    OnItemClickListener listener;
    public TextView planName;
    public ImageView planImage;

    public PlanViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        cardView = (CardView)itemView.findViewById(R.id.cv_plan_container);
        planName = (TextView)itemView.findViewById(R.id.tv_plan_name);
        planImage = (ImageView)itemView.findViewById(R.id.iv_plan_image);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClicked(getAdapterPosition());

    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
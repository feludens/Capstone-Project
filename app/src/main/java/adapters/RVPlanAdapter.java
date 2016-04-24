package adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spadatech.mobile.android.foodframer.R;

import java.util.List;

import models.Plan;

/**
 * Created by Felipe S. Pereira on 4/14/16.
 */
public class RVPlanAdapter extends RecyclerView.Adapter<RVPlanAdapter.PlanViewHolder>{

    private List<Plan> mPlanList;

    public RVPlanAdapter(List<Plan> planList){
        this.mPlanList = planList;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        PlanViewHolder pvh = new PlanViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        holder.planName.setText(mPlanList.get(position).getPlanName());
        holder.planImage.setImageResource(mPlanList.get(position).getImageRsc());
    }

    @Override
    public int getItemCount() {
        return mPlanList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView planName;
        ImageView planImage;

        PlanViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv_plan_container);
            planName = (TextView)itemView.findViewById(R.id.tv_plan_name);
            planImage = (ImageView)itemView.findViewById(R.id.iv_plan_image);
        }
    }

}
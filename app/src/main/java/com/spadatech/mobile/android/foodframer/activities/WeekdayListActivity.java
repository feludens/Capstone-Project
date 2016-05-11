package com.spadatech.mobile.android.foodframer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.adapters.WeekdayAdapter;
import com.spadatech.mobile.android.foodframer.helpers.PlanHelper;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.Weekday;

public class WeekdayListActivity extends AppCompatActivity implements WeekdayAdapter.OnWeekdayClickListener {

    private Plan mPlan;
    private RecyclerView mRecyclerView;
    private WeekdayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekday_list);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getResources().getText(R.string.title_weekday_list_activity));
        }

        mPlan = PlanHelper.get().getActivePlan();
        Log.d("Ludens", "plan name: " + mPlan.getName());

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        mAdapter = new WeekdayAdapter(mPlan.getWeekdaysList(), this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onWeekdayClicked(Weekday weekday) {
        Log.d("Ludens", "weekday name: " + weekday.getWeekdayName());
    }
}

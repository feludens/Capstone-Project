package com.spadatech.mobile.android.foodframer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.adapters.WeekdayAdapter;
import com.spadatech.mobile.android.foodframer.helpers.PlanHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Weekday;

/**
 * Created by Felipe S. Pereira
 */
public class WeekdayListActivity extends AppCompatActivity implements WeekdayAdapter.OnWeekdayClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekday_list);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getResources().getText(R.string.title_weekday_list_activity));
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_weekday);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        WeekdayAdapter mAdapter = new WeekdayAdapter(PlanHelper.get().getActivePlan().getWeekdaysList(), this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onWeekdayClicked(Weekday weekday) {
        WeekdayHelper.get().setWeekday(weekday);
        Intent intent = new Intent(this, DailyPlanActivity.class);
        startActivity(intent);
    }
}

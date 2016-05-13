package com.spadatech.mobile.android.foodframer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.adapters.DailyAdapter;
import com.spadatech.mobile.android.foodframer.models.Weekday;

public class DailyPlanActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Weekday mWeekday;
    private DailyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mLayoutManager = new LinearLayoutManager(MainActivity.this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
////Adapter is created in the last step
//        mAdapter = new CustomAdapter(mDataset, mDataSetTypes);
//        mRecyclerView.setAdapter(mAdapter);
    }
}

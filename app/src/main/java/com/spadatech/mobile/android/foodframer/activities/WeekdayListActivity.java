package com.spadatech.mobile.android.foodframer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.helpers.PlanHelper;
import com.spadatech.mobile.android.foodframer.models.Plan;

public class WeekdayListActivity extends AppCompatActivity {

    private Plan mPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekday_list);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getResources().getText(R.string.title_weekday_list_activity));
        }

        mPlan = PlanHelper.get().getActivePlan();
        Log.d("Ludens", "plan name: " + mPlan.getName());

    }
}

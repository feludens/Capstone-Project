package com.spadatech.mobile.android.foodframer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.User;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class WeekdayListActivity extends AppCompatActivity {

    private Plan mPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekday_list);

        String planName;
        String username = "";
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            planName = intent.getStringExtra("planName");
            username = intent.getStringExtra("username");
        }

        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> result = realm.where(User.class)
                .equalTo("username", username)
                .findAll();

        if(result.size() > 0){
            RealmList<Plan> plans = result.first().getPlanList();
            for (Plan plan : plans) {
//                if(plan.getName().equals(planName))
            }
        }

    }
}

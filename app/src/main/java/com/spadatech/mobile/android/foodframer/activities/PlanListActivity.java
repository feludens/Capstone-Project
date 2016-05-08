package com.spadatech.mobile.android.foodframer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.adapters.RVItemAdapter;
import com.spadatech.mobile.android.foodframer.dialogs.NewPlanDialogFragment;
import com.spadatech.mobile.android.foodframer.helpers.AlertHelper;
import com.spadatech.mobile.android.foodframer.managers.SessionManager;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class PlanListActivity extends AppCompatActivity implements RVItemAdapter.OnPlanClickListener, NewPlanDialogFragment.OnCreatePlanClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyPlanListView;
    private List<Plan> mPlanList;
    private RVItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        mEmptyPlanListView = (LinearLayout) findViewById(R.id.ll_plan_lis_empty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = getSupportFragmentManager();
                    NewPlanDialogFragment editNameDialogFragment = NewPlanDialogFragment.newInstance();
                    editNameDialogFragment.show(fm, editNameDialogFragment.TAG);
                }
            });
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserInfo();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> result = realm.where(User.class)
                .equalTo("username", userInfo.get(SessionManager.KEY_USERNAME))
                .equalTo("email", userInfo.get(SessionManager.KEY_EMAIL))
                .findAll();

        if(result.size() > 0) {
            mPlanList = result.first().getPlanList();
        }else{
            mPlanList = new ArrayList<>();
        }

        mAdapter = new RVItemAdapter(mPlanList, this);
        mRecyclerView.setAdapter(mAdapter);

        if(mPlanList == null || mPlanList.isEmpty()){
            mRecyclerView.setVisibility(View.GONE);
            mEmptyPlanListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeData(){
        mPlanList = new RealmList<>();
        mPlanList.add(new Plan("Bulking", R.drawable.google, null, null));
        mPlanList.add(new Plan("Leaning", R.drawable.google, null, null));
        mPlanList.add(new Plan("Chocolate for Days", R.drawable.google, null, null));
    }

    @Override
    public void onPlanClicked(Plan plan) {
    }

    @Override
    public void onCreatePlanClicked(String planName) {
        if (isNewPlanValid(planName)) {

            SessionManager sessionManager = new SessionManager(this);
            HashMap<String, String> userInfo = sessionManager.getUserInfo();
            Realm realm = Realm.getDefaultInstance();
            User user = realm.where(User.class)
                    .equalTo("username", userInfo.get(SessionManager.KEY_USERNAME))
                    .equalTo("email", userInfo.get(SessionManager.KEY_EMAIL))
                    .findFirst();

            realm.beginTransaction();
            Plan newPlan = realm.createObject(Plan.class);
            newPlan.setName(planName);
            realm.commitTransaction();

            realm.beginTransaction();
            RealmList<Plan> newList = new RealmList<>();
            newList.add(newPlan);
            user.setPlanList(newList);
            realm.commitTransaction();

//            mAdapter.refresh();

        }

    }

    public boolean isNewPlanValid(String planName) {
        boolean isValid = false;
        String alertType;

        if(planName.isEmpty()) {
//            alertType = AlertHelper.AlertType.ALERT_MISSING_PLAN_NAME;
            AlertHelper.showAlertDialog(this, "Missing plan name");
            return isValid;
        }

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserInfo();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> result = realm.where(User.class)
                .equalTo("username", userInfo.get(SessionManager.KEY_USERNAME))
                .equalTo("email", userInfo.get(SessionManager.KEY_EMAIL))
                .findAll();

        if(!result.isEmpty())
        {
            RealmList<Plan> plans = result.first().getPlanList();
            Plan tempPlan = new Plan();
            tempPlan.setName(planName);
            for (Plan plan: plans) {
                if(plan.getName().equals(planName)){
//                    alertType = AlertHelper.AlertType.ALERT_MISSING_PLAN_NAME;
                    AlertHelper.showAlertDialog(this, "Plan already exists");
                    return isValid;
                }
            }
            isValid = true;
        }

        return isValid;
    }
}

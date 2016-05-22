package com.spadatech.mobile.android.foodframer.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.adapters.PlanAdapter;
import com.spadatech.mobile.android.foodframer.dialogs.NewPlanDialogFragment;
import com.spadatech.mobile.android.foodframer.helpers.AlertHelper;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.helpers.PlanHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.managers.SessionManager;
import com.spadatech.mobile.android.foodframer.models.Plan;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Felipe S. Pereira
 */
public class PlanListActivity extends AppCompatActivity implements PlanAdapter.OnPlanClickListener, NewPlanDialogFragment.OnCreatePlanClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyPlanListView;
    private PlanAdapter mAdapter;
    private HashMap<String, String> mUserInfo;
    private List<Plan> mPlanList;

    // private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        mUserInfo = SessionManager.get(this).getUserInfo();
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

//        fillData();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);


//        mAdapter = new PlanAdapter(mPlanList, this);
//        mRecyclerView.setAdapter(mAdapter);
//
//        if(mPlanList == null || mPlanList.isEmpty()){
//            mRecyclerView.setVisibility(View.GONE);
//            mEmptyPlanListView.setVisibility(View.VISIBLE);
//        }

        getSupportLoaderManager().initLoader(1, null, this);
    }

//    private void fillData() {
//        String[] from = new String[] { Plan.TABLE };
//        // Fields on the UI to which we map
//        int[] to = new int[] { R.id.label };
//
//        getLoaderManager().initLoader(0, null, this);
//        adapter = new SimpleCursorAdapter(this, R.layout.todo_row, null, from,
//                to, 0);
//
//        setListAdapter(adapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plan_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPlanClicked(Plan plan) {
        PlanHelper.get().setActivePlan(plan);
        Intent intent = new Intent(this, WeekdayListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreatePlanClicked(String planName) {
        if (isNewPlanValid(planName)) {

            Plan newPlan = new Plan();
            newPlan.setName(planName);
            newPlan.setImage(R.drawable.food);
            newPlan.setUsername(mUserInfo.get(SessionManager.KEY_USERNAME));

            ContentValues values = new ContentValues();
            values.put(Plan.KEY_PLAN_NAME, newPlan.getName());
            values.put(Plan.KEY_PLAN_IMAGE, newPlan.getImage());
            values.put(Plan.KEY_PLAN_USERNAME, newPlan.getUsername());

            Uri uri = DatabaseHelper.PLAN_CONTENT_URI;
            getContentResolver().insert(uri, values);


            String whereClause = "planname = ? AND Username = ?";
            String[] selectionArgs = {newPlan.getName(), newPlan.getUsername()};
            Cursor cursor = null;

            Uri urii = DatabaseHelper.PLAN_CONTENT_URI;
            cursor = getContentResolver().query(urii, null, whereClause, selectionArgs, null);
            int planId = 0;


            if(cursor != null) {
                if(cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        planId = cursor.getInt(cursor.getColumnIndex(Plan.KEY_PLAN_ID));
                        newPlan.setId(planId);
                    }
                }
            }

            createWeekdays(planId);
            refreshViews();
        }

    }

    private void createWeekdays(int planId) {
        for(int i = 0; i < WeekdayHelper.getWeekdayNameList().size(); i ++){
            int resourceId = getResources().getIdentifier(WeekdayHelper.getWeekdayNameList().get(i).toLowerCase(), "drawable", getPackageName());

            ContentValues values = new ContentValues();
            values.put(Weekday.KEY_WEEKDAY_NAME, WeekdayHelper.getWeekdayNameList().get(i));
            values.put(Weekday.KEY_WEEKDAY_PLAN_ID, planId);
            values.put(Weekday.KEY_WEEKDAY_ORDER, i+1);
            values.put(Weekday.KEY_WEEKDAY_IMAGE, resourceId);

            Uri uri = DatabaseHelper.WEEKDAY_CONTENT_URI;
            getContentResolver().insert(uri, values);
        }
    }

    private void refreshViews() {
        if(mEmptyPlanListView.getVisibility() == View.VISIBLE){
            mEmptyPlanListView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    public boolean isNewPlanValid(String planName) {
        boolean isValid = false;

        if(planName.isEmpty()) {
            AlertHelper.showAlertDialog(this, getString(R.string.alert_missing_plan_name));
            return isValid;
        }

//        String whereClause = "planname = ?";
//        String[] selectionArgs = {planName};
//        Cursor cursor = null;
//
//        Uri uri = DatabaseHelper.USER_CONTENT_URI;
//        cursor = getContentResolver().query(uri, null, whereClause, selectionArgs, null);
//
//        if(cursor != null) {
//            if(cursor.getCount() > 0) {
//                while (cursor.moveToNext()) {
//                    isValid = false;
//                }
//            }else{
//                isValid = true;
//            }
//        }else{
//            isValid = true;
//        }

        isValid = true;
        return isValid;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String whereClause = "username = ?";
        String[] selectionArgs = {mUserInfo.get(SessionManager.KEY_USERNAME)};
        Uri uri = DatabaseHelper.PLAN_CONTENT_URI;

        CursorLoader cursorLoader = new CursorLoader(this, uri, null, whereClause, selectionArgs, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        mAdapter = new PlanAdapter(this, data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}

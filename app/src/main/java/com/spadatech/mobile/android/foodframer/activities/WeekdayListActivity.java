package com.spadatech.mobile.android.foodframer.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.adapters.WeekdayAdapter;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.helpers.PlanHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Weekday;

/**
 * Created by Felipe S. Pereira
 */
public class WeekdayListActivity extends AppCompatActivity implements WeekdayAdapter.OnWeekdayClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private WeekdayAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekday_list);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getResources().getText(R.string.title_weekday_list_activity));
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_weekday);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onWeekdayClicked(Weekday weekday) {
        WeekdayHelper.get().setWeekday(weekday);
        Intent intent = new Intent(this, DailyPlanActivity.class);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String whereClause = "planid = " + PlanHelper.get().getActivePlan().getId();
        Uri uri = DatabaseHelper.WEEKDAY_CONTENT_URI;

        CursorLoader cursorLoader = new CursorLoader(this, uri, null, whereClause, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        mAdapter = new WeekdayAdapter(this, data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

package com.spadatech.mobile.android.foodframer.activities;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.adapters.DailyAdapter;
import com.spadatech.mobile.android.foodframer.dialogs.NewGroceryDialogFragment;
import com.spadatech.mobile.android.foodframer.dialogs.NewMealDialogFragment;
import com.spadatech.mobile.android.foodframer.dialogs.NewPrepdayDialogFragment;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Weekday;

/**
 * Created by Felipe S. Pereira
 */
public class DailyPlanActivity extends AppCompatActivity
        implements FloatingActionsMenu.OnFloatingActionsMenuUpdateListener, NewGroceryDialogFragment.OnCreateGroceryClickListener, NewMealDialogFragment.OnCreateMealClickListener, NewPrepdayDialogFragment.OnCreatePrepdayClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int GROCERY_LOADER = 0;
    private static final int MEAL_LOADER = 1;
    private static final int PREPDAY_LOADER = 2;
    private LinearLayout mEmptyPlanListView;
    private LinearLayout mTransparentScreen;
    private Weekday mWeekday;

    private DailyAdapter mAdapter;
    private DailyAdapter mMealsAdapter;
    private DailyAdapter mPrepdaysAdapter;

    private RecyclerView mRecyclerViewGroceries;
    private RecyclerView mRecyclerViewMeals;
    private RecyclerView mRecyclerViewPrepdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

        mWeekday = WeekdayHelper.get().getWeekday();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(mWeekday.getName() + getResources().getText(R.string.title_daily_plan));
        }

        mTransparentScreen = (LinearLayout) findViewById(R.id.ll_transparent_screen);
        mEmptyPlanListView = (LinearLayout) findViewById(R.id.ll_daily_list_empty);

        mRecyclerViewGroceries = (RecyclerView) findViewById(R.id.rv_daily_groceries);
        mRecyclerViewMeals = (RecyclerView) findViewById(R.id.rv_daily_meals);
        mRecyclerViewPrepdays = (RecyclerView) findViewById(R.id.rv_daily_prepdays);
        mRecyclerViewGroceries.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewMeals.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewPrepdays.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(GROCERY_LOADER, null, this);
        getSupportLoaderManager().initLoader(MEAL_LOADER, null, this);
        getSupportLoaderManager().initLoader(PREPDAY_LOADER, null, this);
//
        setFabOnClickListeners();

    }

    private void setFabOnClickListeners() {
        final FloatingActionsMenu mainFab = (FloatingActionsMenu) findViewById(R.id.fab_multiple_actions);
        if(mainFab != null) {
            mainFab.setOnFloatingActionsMenuUpdateListener(this);
        }

        FloatingActionButton groceryFab = (FloatingActionButton) findViewById(R.id.action_new_grocery);
        if(groceryFab != null) {
            groceryFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTransparentScreen.getVisibility() == View.VISIBLE) {
                        mTransparentScreen.setVisibility(View.GONE);
                    }

                    if(mainFab != null) {
                        mainFab.toggle();
                    }

                    FragmentManager fm = getSupportFragmentManager();
                    NewGroceryDialogFragment newGroceryDialogFragment = NewGroceryDialogFragment.newInstance();
                    newGroceryDialogFragment.show(fm, newGroceryDialogFragment.TAG);
                }
            });
        }

        FloatingActionButton mealFab = (FloatingActionButton) findViewById(R.id.action_new_meal);
        if(mealFab != null) {
            mealFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTransparentScreen.getVisibility() == View.VISIBLE) {
                        mTransparentScreen.setVisibility(View.GONE);
                    }

                    if(mainFab != null) {
                        mainFab.toggle();
                    }

                    FragmentManager fm = getSupportFragmentManager();
                    NewMealDialogFragment newMealDialogFragment = NewMealDialogFragment.newInstance();
                    newMealDialogFragment.show(fm, newMealDialogFragment.TAG);
                }
            });
        }

        FloatingActionButton prepFab = (FloatingActionButton) findViewById(R.id.action_new_prep);
        if(prepFab != null) {
            prepFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTransparentScreen.getVisibility() == View.VISIBLE) {
                        mTransparentScreen.setVisibility(View.GONE);
                    }

                    if(mainFab != null) {
                        mainFab.toggle();
                    }

                    FragmentManager fm = getSupportFragmentManager();
                    NewPrepdayDialogFragment newPrepDialogFragment = NewPrepdayDialogFragment.newInstance();
                    newPrepDialogFragment.show(fm, newPrepDialogFragment.TAG);
                }
            });
        }

    }

    @Override
    public void onMenuExpanded() {
        if (mTransparentScreen.getVisibility() == View.GONE) {
            mTransparentScreen.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMenuCollapsed() {
        if (mTransparentScreen.getVisibility() == View.VISIBLE) {
            mTransparentScreen.setVisibility(View.GONE);
        }
    }

    //TODO: delete below 4 methods - NO LONGER NEEDED
    @Override
    public void onCreateGroceryClicked() {
        updateRealm();
    }

    @Override
    public void onCreatePrepdayClicked() {
        updateRealm();
    }

    @Override
    public void onCreateMealClicked() {
        updateRealm();
    }

    private void updateRealm(){
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;
        String whereClause = "WeekdayId = ?";
        String[] selectionArgs = {mWeekday.getId()+""};
        Uri uri = null;
        switch (id){
            case GROCERY_LOADER:
                uri = DatabaseHelper.GROCERY_CONTENT_URI;
                break;

            case MEAL_LOADER:
                uri = DatabaseHelper.MEAL_CONTENT_URI;
                break;

            case PREPDAY_LOADER:
                uri = DatabaseHelper.PREPDAY_CONTENT_URI;
                break;
        }

        cursorLoader = new CursorLoader(this, uri, null, whereClause, selectionArgs, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        switch (loader.getId()){
            case GROCERY_LOADER:
                mAdapter = new DailyAdapter(this, data);
                mRecyclerViewGroceries.setAdapter(mAdapter);
                break;

            case MEAL_LOADER:
                mMealsAdapter = new DailyAdapter(this, data);
                mRecyclerViewMeals.setAdapter(mMealsAdapter);
                break;

            case PREPDAY_LOADER:
                mPrepdaysAdapter = new DailyAdapter(this, data);
                mRecyclerViewPrepdays.setAdapter(mPrepdaysAdapter);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

package com.spadatech.mobile.android.foodframer.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.spadatech.mobile.android.foodframer.helpers.RealmHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.PrepDay;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Felipe S. Pereira
 */
public class DailyPlanActivity extends AppCompatActivity
        implements FloatingActionsMenu.OnFloatingActionsMenuUpdateListener, NewGroceryDialogFragment.OnCreateGroceryClickListener, NewMealDialogFragment.OnCreateMealClickListener, NewPrepdayDialogFragment.OnCreatePrepdayClickListener {

    private LinearLayout mEmptyPlanListView;
    private LinearLayout mTransparentScreen;
    private Weekday mWeekday;
    private DailyAdapter mAdapter;
    private DailyAdapter mMealsAdapter;
    private DailyAdapter mPrepdaysAdapter;

    private RecyclerView mRecyclerViewGroceries;
    private RecyclerView mRecyclerViewMeals;
    private RecyclerView mRecyclerViewPrepdays;
    private RealmList<Grocery> groceries;
    private RealmList<Meal> meals;
    private RealmList<PrepDay> prepdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

        mWeekday = WeekdayHelper.get().getWeekday();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(mWeekday.getWeekdayName() + getResources().getText(R.string.title_daily_plan));
        }

        groceries = new RealmList<>();
        meals = new RealmList<>();
        prepdays = new RealmList<>();
        populateDataSet();

        mTransparentScreen = (LinearLayout) findViewById(R.id.ll_transparent_screen);
        mEmptyPlanListView = (LinearLayout) findViewById(R.id.ll_daily_list_empty);

        mRecyclerViewGroceries = (RecyclerView) findViewById(R.id.rv_daily_groceries);
        mRecyclerViewMeals = (RecyclerView) findViewById(R.id.rv_daily_meals);
        mRecyclerViewPrepdays = (RecyclerView) findViewById(R.id.rv_daily_prepdays);
        mRecyclerViewGroceries.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewMeals.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewPrepdays.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new DailyAdapter(groceries);
        mMealsAdapter = new DailyAdapter(meals);
        mPrepdaysAdapter = new DailyAdapter(prepdays);
        mRecyclerViewGroceries.setAdapter(mAdapter);
        mRecyclerViewMeals.setAdapter(mMealsAdapter);
        mRecyclerViewPrepdays.setAdapter(mPrepdaysAdapter);

        if(meals.isEmpty() && groceries.isEmpty() && prepdays.isEmpty()){
            mEmptyPlanListView.setVisibility(View.VISIBLE);
        }

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
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        mWeekday = RealmHelper.get().getCurrentWeekday(this);
        realm.commitTransaction();

        populateDataSet();

        refreshViews();
    }

    private void populateDataSet() {
        if(mWeekday.getGroceries() != null && !mWeekday.getGroceries().isEmpty()) {
            this.groceries = mWeekday.getGroceries();
        }

        if(mWeekday.getMeals() != null && !mWeekday.getMeals().isEmpty()) {
            this.meals = mWeekday.getMeals();
        }

        if(mWeekday.getPrepdays() != null && !mWeekday.getPrepdays().isEmpty()) {
            this.prepdays = mWeekday.getPrepdays();
        }
    }


    private void refreshViews() {
        if(mEmptyPlanListView.getVisibility() == View.VISIBLE){
            mEmptyPlanListView.setVisibility(View.GONE);
        }

        //TODO: I currently had to re-create the adapter instead of swapping the data source.
        //TODO: that is do to the limitations of Realm. Keep an eye for changes then update this!

//        mMealsAdapter.swap(meals);
//        mPrepdaysAdapter.swap(prepdays);
//        mAdapter.swap(groceries);

        mAdapter = new DailyAdapter(groceries);
        mMealsAdapter = new DailyAdapter(meals);
        mPrepdaysAdapter = new DailyAdapter(prepdays);
        mRecyclerViewGroceries.setAdapter(mAdapter);
        mRecyclerViewMeals.setAdapter(mMealsAdapter);
        mRecyclerViewPrepdays.setAdapter(mPrepdaysAdapter);
    }
}

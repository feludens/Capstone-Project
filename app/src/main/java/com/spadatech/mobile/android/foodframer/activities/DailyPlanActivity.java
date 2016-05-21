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
import com.spadatech.mobile.android.foodframer.helpers.Constants;
import com.spadatech.mobile.android.foodframer.helpers.RealmHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.Prep;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Felipe S. Pereira
 */
public class DailyPlanActivity extends AppCompatActivity
        implements FloatingActionsMenu.OnFloatingActionsMenuUpdateListener, NewGroceryDialogFragment.OnCreateGroceryClickListener, NewMealDialogFragment.OnCreateMealClickListener, NewPrepdayDialogFragment.OnCreatePrepdayClickListener {

    private RecyclerView mRecyclerViewGroceries;
    private RecyclerView mRecyclerViewMeals;
    private RecyclerView mRecyclerViewPrepdays;

    private LinearLayout mEmptyPlanListView;
    private LinearLayout mTransparentScreen;
    private Weekday mWeekday;
    private DailyAdapter mAdapter;
    private DailyAdapter mMealsAdapter;
    private DailyAdapter mPrepdaysAdapter;
    private List<Map<Integer, List>> mDataSet;
    private boolean isEmpty = true;

    RealmList<Grocery> groceries;
    RealmList<Meal> meals;
    RealmList<Prep> prepdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

        mWeekday = WeekdayHelper.get().getWeekday();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(mWeekday.getWeekdayName() + getResources().getText(R.string.title_daily_plan));
        }

        mDataSet = new ArrayList<>();
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

//        if(isEmpty){
//            mRecyclerViewGroceries.setVisibility(View.GONE);
//            mEmptyPlanListView.setVisibility(View.VISIBLE);
//        }else{
//            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//            mRecyclerViewGroceries.setLayoutManager(layoutManager);
//            mRecyclerViewGroceries.setAdapter(mAdapter);
//        }



        if(meals.isEmpty() && groceries.isEmpty() && prepdays.isEmpty()){
//            mRecyclerViewGroceries.setVisibility(View.GONE);
//            mRecyclerViewMeals.setVisibility(View.GONE);
//            mRecyclerViewPrepdays.setVisibility(View.GONE);
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
    public void onCreateGroceryClicked(RealmList groceries) {
        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        mWeekday.setGroceryList(groceries);
//        realm.commitTransaction();
        realm.beginTransaction();
        mWeekday = RealmHelper.get().getCurrentWeekday(this);
        realm.commitTransaction();

        mDataSet.clear();
        populateDataSet();

        refreshViews();
    }

    @Override
    public void onCreatePrepdayClicked(Prep prepday) {
        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        RealmList<Prep> preps;
//        if(mWeekday.getPrepdays() == null || mWeekday.getPrepdays().isEmpty()){
//            preps = new RealmList<>();
//        }else{
//            preps = mWeekday.getPrepdays();
//        }
//        realm.copyToRealm(preps);
//        realm.commitTransaction();
//
//        realm.beginTransaction();
//        preps.add(prepday);
//        realm.commitTransaction();
//
//        realm.beginTransaction();
//        mWeekday.setPrepList(preps);
//        realm.commitTransaction();

        realm.beginTransaction();
        mWeekday = RealmHelper.get().getCurrentWeekday(this);
        realm.commitTransaction();

        mDataSet.clear();
        populateDataSet();

        refreshViews();
    }

    @Override
    public void onCreateMealClicked(Meal meal) {
        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
        RealmList<Meal> meals = RealmHelper.get().getCurrentWeekdayMeal(this);
//        RealmList<Meal> meals2;
//        if(mWeekday.getMeals() == null || mWeekday.getMeals().isEmpty()){
//            meals2 = new RealmList<>();
//        }else{
//            meals2 = mWeekday.getMeals();
//        }
//        realm.copyToRealm(meals);
//        realm.commitTransaction();

//        realm.beginTransaction();
//        meals.add(meals);
////        realm.copyToRealm(meals);
//        realm.commitTransaction();

        realm.beginTransaction();
        mWeekday = RealmHelper.get().getCurrentWeekday(this);
        realm.commitTransaction();



        mDataSet.clear();
        populateDataSet();

        refreshViews();
    }

    private void populateDataSet() {
        RealmList<Meal> meals = RealmHelper.get().getCurrentWeekdayMeal(this);
        Map<Integer, List> groceryMap = new HashMap<>();
        if(mWeekday.getGroceries() != null && !mWeekday.getGroceries().isEmpty()) {
            groceryMap.put(Constants.VIEW_TYPE_GROCERY, mWeekday.getGroceries());
            mDataSet.add(groceryMap);
            this.groceries = mWeekday.getGroceries();
        }

        Map<Integer, List> mealMap = new HashMap<>();
        if(mWeekday.getMeals() != null && !mWeekday.getMeals().isEmpty()) {
            mealMap.put(Constants.VIEW_TYPE_MEAL, mWeekday.getMeals());
            mDataSet.add(mealMap);
            this.meals = mWeekday.getMeals();
        }

        Map<Integer, List> prepMap = new HashMap<>();
        if(mWeekday.getPrepdays() != null && !mWeekday.getPrepdays().isEmpty()) {
            prepMap.put(Constants.VIEW_TYPE_PREP, mWeekday.getPrepdays());
            mDataSet.add(prepMap);
            this.prepdays = mWeekday.getPrepdays();
        }

        if(!groceryMap.isEmpty()
                || !mealMap.isEmpty()
                || !prepMap.isEmpty()){
            isEmpty = false;
        }
    }


    private void refreshViews() {
        if(mEmptyPlanListView.getVisibility() == View.VISIBLE){
//            mRecyclerViewGroceries.setVisibility(View.VISIBLE);
//            mRecyclerViewMeals.setVisibility(View.VISIBLE);
//            mRecyclerViewPrepdays.setVisibility(View.VISIBLE);
            mEmptyPlanListView.setVisibility(View.GONE);
        }

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mMealsAdapter.notifyDataSetChanged();
//                mPrepdaysAdapter.notifyDataSetChanged();
//                mAdapter.notifyDataSetChanged();
//            }
//        });

//        mAdapter = new DailyAdapter(groceries);
//        mMealsAdapter = new DailyAdapter(meals);
//        mPrepdaysAdapter = new DailyAdapter(prepdays);
//        mRecyclerViewGroceries.setAdapter(mAdapter);
//        mRecyclerViewMeals.setAdapter(mMealsAdapter);
//        mRecyclerViewPrepdays.setAdapter(mPrepdaysAdapter);

        mMealsAdapter.swap(meals);
        mPrepdaysAdapter.swap(prepdays);
        mAdapter.swap(groceries);

    }
}

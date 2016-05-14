package com.spadatech.mobile.android.foodframer.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.adapters.DailyAdapter;
import com.spadatech.mobile.android.foodframer.dialogs.NewGroceryDialogFragment;
import com.spadatech.mobile.android.foodframer.helpers.Constants;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyPlanActivity extends AppCompatActivity
        implements FloatingActionsMenu.OnFloatingActionsMenuUpdateListener, NewGroceryDialogFragment.OnCreateGroceryClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyPlanListView;
    private LinearLayout mTransparentScreen;
    private Weekday mWeekday;
    private DailyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

        mWeekday = WeekdayHelper.get().getWeekday();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(mWeekday.getWeekdayName() + getResources().getText(R.string.title_daily_plan));
        }

        List<Map<Integer, List>> dataSet = new ArrayList<>();
        Map<Integer, List> map = new HashMap<>();
        map.put(Constants.VIEW_TYPE_GROCERY, mWeekday.getGroceries());
        map.put(Constants.VIEW_TYPE_MEAL, mWeekday.getMeals());
        map.put(Constants.VIEW_TYPE_PREP, mWeekday.getPrepdays());
        dataSet.add(map);

        boolean isEmpty = true;

        if(!map.get(Constants.VIEW_TYPE_GROCERY).isEmpty()
                || !map.get(Constants.VIEW_TYPE_MEAL).isEmpty()
                || !map.get(Constants.VIEW_TYPE_PREP).isEmpty()){
            isEmpty = false;
        }

        mTransparentScreen = (LinearLayout) findViewById(R.id.ll_transparent_screen);
        mEmptyPlanListView = (LinearLayout) findViewById(R.id.ll_daily_list_empty);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_daily);
        mAdapter = new DailyAdapter(dataSet);

        if(isEmpty){
            mRecyclerView.setVisibility(View.GONE);
            mEmptyPlanListView.setVisibility(View.VISIBLE);
        }else{
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
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
                    // Your FAB click action here...
                    Toast.makeText(getBaseContext(), "FAB Clicked", Toast.LENGTH_SHORT).show();
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
    public void onCreateGroceryClicked(List groceries) {

    }
}

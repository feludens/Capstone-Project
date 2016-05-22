package com.spadatech.mobile.android.foodframer.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.adapters.MealItemListAdapter;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.MealItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/6/16.
 */
public class NewMealDialogFragment extends DialogFragment{

    public final String TAG = getClass().getSimpleName();
    private EditText mMealName;
    private EditText mMealItemName;
    private EditText mMealNote;
    private List<MealItem> mMealItemList;
    private MealItemListAdapter mAdapter;

    public NewMealDialogFragment() {
    }

    public static NewMealDialogFragment newInstance() {
        NewMealDialogFragment frag = new NewMealDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_new_meal, null);

        mMealName = (EditText) v.findViewById(R.id.et_new_meal_name);
        mMealNote = (EditText) v.findViewById(R.id.et_new_meal_notes);
        mMealItemName = (EditText) v.findViewById(R.id.et_new_meal_item_name);
        Button mAddButton = (Button) v.findViewById(R.id.button_add_new_meal);
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_new_meals);
        mMealItemList = new ArrayList<>();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMealName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Enter a Meal Name.", Toast.LENGTH_SHORT).show();
                }else if(mMealItemName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Enter a Dish Name.", Toast.LENGTH_SHORT).show();
                }else{
                    MealItem newItem = new MealItem();
                    newItem.setName(mMealItemName.getText().toString());

                    mMealItemList.add(newItem);

                    mAdapter.notifyDataSetChanged();
                    mMealItemName.setText("");
                }
            }
        });

        mMealName.requestFocus();
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setTitle(R.string.new_meal);
        alertDialogBuilder.setPositiveButton(R.string.create,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mMealItemList.isEmpty()){
                    Toast.makeText(getActivity(), getString(R.string.toast_add_one_dish), Toast.LENGTH_SHORT).show();
                }else {
                    Meal newMeal = new Meal();
                    newMeal.setName(mMealName.getText().toString());
                    newMeal.setNote(mMealNote.getText().toString());
                    newMeal.setWeekdayId(WeekdayHelper.get().getWeekday().getId());

                    ContentValues values = new ContentValues();
                    values.put(Meal.KEY_MEAL_NAME, newMeal.getName());
                    values.put(Meal.KEY_MEAL_NOTE, newMeal.getNote());
                    values.put(Meal.KEY_MEAL_WEEKDAY_ID, newMeal.getWeekdayId());

                    Uri uri = DatabaseHelper.MEAL_CONTENT_URI;
                    getActivity().getContentResolver().insert(uri, values);

                    // Try to query the newly created Meal and get it's id
                    String whereClause = "MealName = ? AND WeekdayId = ?";
                    String[] selectionArgs = {newMeal.getName(), newMeal.getWeekdayId()+""};
                    Cursor cursor = getActivity().getContentResolver().query(uri, null, whereClause, selectionArgs, null);
                    int mealId = 0;

                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                mealId = cursor.getInt(cursor.getColumnIndex(Meal.KEY_MEAL_ID));
                                newMeal.setId(mealId);
                            }
                        }
                    }

                    // Create new Meal Items
                    for(int i = 0; i < mMealItemList.size(); i++){
                        ContentValues valuess = new ContentValues();
                        valuess.put(MealItem.KEY_MEAL_ITEM_NAME, mMealItemList.get(i).getName());
                        valuess.put(MealItem.KEY_MEAL_ITEM_MEAL_ID, newMeal.getId());

                        Uri urii = DatabaseHelper.MEAL_ITEM_CONTENT_URI;
                        getActivity().getContentResolver().insert(urii, valuess);
                    }
                }
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mAdapter = new MealItemListAdapter(mMealItemList, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return alertDialogBuilder.show();
    }
}

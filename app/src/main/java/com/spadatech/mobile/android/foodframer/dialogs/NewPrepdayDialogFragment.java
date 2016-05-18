package com.spadatech.mobile.android.foodframer.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.spadatech.mobile.android.foodframer.models.Meal;
import com.spadatech.mobile.android.foodframer.models.MealItem;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Felipe S. Pereira on 5/6/16.
 */
public class NewPrepdayDialogFragment extends DialogFragment{

    public final String TAG = getClass().getSimpleName();
    private OnCreateMealClickListener mListener;
    private EditText mMealName;
    private EditText mMealItemName;
    private EditText mMealNote;
    private Button mAddButton;
    private RecyclerView mRecyclerView;
    private RealmList<MealItem> mNewMealItemList;
    private Meal mMeal;
    private MealItemListAdapter mAdapter;

    public NewPrepdayDialogFragment() {
    }

    public static NewPrepdayDialogFragment newInstance() {
        NewPrepdayDialogFragment frag = new NewPrepdayDialogFragment();
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
        mAddButton = (Button) v.findViewById(R.id.button_add_new_meal);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_new_meals);
        mNewMealItemList = new RealmList<>();

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        mMeal = realm.createObject(Meal.class);
        mMeal.setMealName("MealPlaceHolderName");
        realm.commitTransaction();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new GroceryItem realm object
                // Add new grocery item to the list

                if(mMealName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Enter a Meal Name.", Toast.LENGTH_SHORT).show();
                }else if(mMealItemName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Enter a Dish Name.", Toast.LENGTH_SHORT).show();
                }else{
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    MealItem newItem = realm.createObject(MealItem.class);
                    newItem.setMealItemName(mMealItemName.getText().toString());
                    realm.commitTransaction();

                    realm.beginTransaction();
                    mNewMealItemList.add(newItem);
                    mMeal.setMealItemList(mNewMealItemList);
                    realm.commitTransaction();

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
                if(mNewMealItemList.isEmpty()){
                    Toast.makeText(getActivity(), "Add at least one Dish.", Toast.LENGTH_SHORT).show();
                }else {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealm(mNewMealItemList);
                    realm.commitTransaction();

                    realm.beginTransaction();
                    mMeal.setMealName(mMealName.getText().toString());
                    mMeal.setMealNotes(mMealNote.getText().toString());
                    mMeal.setMealItemList(mNewMealItemList);
                    realm.commitTransaction();

                    mListener.onCreateMealClicked(mMeal);
                }
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mAdapter = new MealItemListAdapter(mNewMealItemList, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return alertDialogBuilder.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnCreateMealClickListener){
            mListener = (OnCreateMealClickListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCreateMealClickListener {
        void onCreateMealClicked(Meal meal);
    }
}

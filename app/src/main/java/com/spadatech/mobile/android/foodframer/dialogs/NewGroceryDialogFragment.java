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
import com.spadatech.mobile.android.foodframer.adapters.GroceryItemListAdapter;
import com.spadatech.mobile.android.foodframer.helpers.RealmHelper;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Felipe S. Pereira on 5/6/16.
 */
public class NewGroceryDialogFragment extends DialogFragment{

    public final String TAG = getClass().getSimpleName();
    private OnCreateGroceryClickListener mListener;
    private EditText mEditText;
    private Button mAddButton;
    private RecyclerView mRecyclerView;
    private RealmList<GroceryItem> mNewGroceriesList;
    private Grocery mGrocery;
    GroceryItemListAdapter mAdapter;

    public NewGroceryDialogFragment() {
    }

    public static NewGroceryDialogFragment newInstance() {
        NewGroceryDialogFragment frag = new NewGroceryDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_new_grocery, null);

        mEditText = (EditText) v.findViewById(R.id.et_new_grocery_name);
        mAddButton = (Button) v.findViewById(R.id.button_add_new_grocery);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_new_groceries);
        mNewGroceriesList = new RealmList<>();
        mGrocery = new Grocery();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new GroceryItem realm object
                // Add new grocery item to the list

                if(mEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Enter a value", Toast.LENGTH_SHORT).show();
                }else{
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    GroceryItem newItem = realm.createObject(GroceryItem.class);
                    newItem.setGroceryItemName(mEditText.getText().toString());
                    newItem.setIsChecked(false);
                    realm.commitTransaction();

                    realm.beginTransaction();
                    mNewGroceriesList.add(newItem);
                    realm.commitTransaction();

                    mAdapter.notifyDataSetChanged();
                    mEditText.setText("");

                }
            }
        });

        mEditText.requestFocus();
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setTitle(R.string.new_grocery_list);
        alertDialogBuilder.setPositiveButton(R.string.create,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealm(mNewGroceriesList);
                realm.commitTransaction();

                Weekday weekday = RealmHelper.get().getCurrentWeekday(getActivity());

                realm.beginTransaction();
                mGrocery = realm.createObject(Grocery.class);
                mGrocery.setGroceryName("Grocery List");
                mGrocery.setWeekdayName(weekday.getWeekdayName());
                realm.commitTransaction();

                Grocery grocery1 = realm.where(Grocery.class).equalTo("weekdayName", weekday.getWeekdayName())
                        .equalTo("mGroceryName", mGrocery.getGroceryName()).findAll().first();

                realm.beginTransaction();
                for(int i = 0; i < mNewGroceriesList.size(); i++){
                    GroceryItem item = mNewGroceriesList.get(i);
                    GroceryItem newItem = realm.createObject(GroceryItem.class);
                    newItem.setGroceryItemName(item.getGroceryItemName());
                    newItem.setIsChecked(item.isIsChecked());
                    grocery1.getmGroceryItemList().add(newItem);
                }
                realm.commitTransaction();

                Grocery groceryz = realm.where(Grocery.class).equalTo("weekdayName", weekday.getWeekdayName())
                        .equalTo("mGroceryName", mGrocery.getGroceryName()).findAll().first();
                realm.beginTransaction();
                weekday.getGroceries().add(groceryz);
                realm.commitTransaction();

                mListener.onCreateGroceryClicked(mNewGroceriesList);


            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mAdapter = new GroceryItemListAdapter(mNewGroceriesList, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return alertDialogBuilder.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnCreateGroceryClickListener){
            mListener = (OnCreateGroceryClickListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCreateGroceryClickListener{
        void onCreateGroceryClicked(RealmList groceries);
    }
}

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
import com.spadatech.mobile.android.foodframer.adapters.PrepdayItemListAdapter;
import com.spadatech.mobile.android.foodframer.helpers.RealmHelper;
import com.spadatech.mobile.android.foodframer.models.MealItem;
import com.spadatech.mobile.android.foodframer.models.Prep;
import com.spadatech.mobile.android.foodframer.models.Weekday;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Felipe S. Pereira on 5/6/16.
 */
public class NewPrepdayDialogFragment extends DialogFragment{

    public final String TAG = getClass().getSimpleName();
    private OnCreatePrepdayClickListener mListener;
    private EditText mPrepdayName;
    private EditText mItemName;
    private EditText mItemNote;
    private RealmList<MealItem> mNewPrepdayItemList;
    private Prep mPrepday;
    private PrepdayItemListAdapter mAdapter;

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
        View v = inflater.inflate(R.layout.dialog_new_prepday, null);

        mPrepdayName = (EditText) v.findViewById(R.id.et_new_prepday_name);
        mItemName = (EditText) v.findViewById(R.id.et_new_prepday_item_name);
        mItemNote = (EditText) v.findViewById(R.id.et_new_prepday_item_notes);
        Button mAddButton = (Button) v.findViewById(R.id.button_add_new_prepday_item);
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_new_dishes);
        mNewPrepdayItemList = new RealmList<>();

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        mPrepday = realm.createObject(Prep.class);
        mPrepday.setPrepName("PrepdayPlaceHolderName");
        realm.commitTransaction();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), R.string.toast_enter_dish_name, Toast.LENGTH_SHORT).show();
                }else{
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    MealItem newItem = realm.createObject(MealItem.class);
                    newItem.setMealItemName(mItemName.getText().toString());
                    newItem.setMealItemNotes(mItemNote.getText().toString());
                    realm.commitTransaction();

                    realm.beginTransaction();
                    mNewPrepdayItemList.add(newItem);
                    realm.commitTransaction();

                    mAdapter.notifyDataSetChanged();
                    mItemName.setText("");
                    mItemNote.setText("");
                }
            }
        });

        mPrepdayName.requestFocus();
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setTitle(R.string.new_meal);
        alertDialogBuilder.setPositiveButton(R.string.create,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mNewPrepdayItemList.isEmpty()){
                    Toast.makeText(getActivity(), R.string.toast_add_one_dish, Toast.LENGTH_SHORT).show();
                }if(mPrepdayName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), R.string.toast_enter_prepday_name, Toast.LENGTH_SHORT).show();
                }else {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealm(mNewPrepdayItemList);
                    realm.commitTransaction();

                    realm.beginTransaction();
                    mPrepday.setPrepName(mPrepdayName.getText().toString());
                    realm.commitTransaction();

                    realm.beginTransaction();
                    Prep newPrep = realm.createObject(Prep.class);
                    newPrep.setPrepName(mPrepday.getPrepName());
                    realm.commitTransaction();

                    realm.beginTransaction();
                    Prep prep = realm.where(Prep.class).equalTo("mPrepName", mPrepday.getPrepName()).findAll().first();
                    for(int i = 0; i < mNewPrepdayItemList.size(); i++){
                        MealItem item = mNewPrepdayItemList.get(i);
                        MealItem newItem = realm.createObject(MealItem.class);
                        newItem.setMealItemName(item.getMealItemName());
                        newItem.setMealItemNotes(item.getMealItemNotes());
                        prep.getmMealItemsList().add(newItem);
                    }
                    realm.commitTransaction();

                    Weekday weekday = RealmHelper.get().getCurrentWeekday(getActivity());
                    Prep prepz = realm.where(Prep.class).equalTo("mPrepName", mPrepday.getPrepName()).findAll().first();
                    realm.beginTransaction();
                    weekday.getPrepdays().add(prepz);
                    realm.commitTransaction();

                    mPrepday = prepz;

                    mListener.onCreatePrepdayClicked();


                }
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mAdapter = new PrepdayItemListAdapter(mNewPrepdayItemList, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return alertDialogBuilder.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnCreatePrepdayClickListener){
            mListener = (OnCreatePrepdayClickListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCreatePrepdayClickListener {
        void onCreatePrepdayClicked();
    }
}

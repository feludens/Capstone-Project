package com.spadatech.mobile.android.foodframer.dialogs;

import android.app.Activity;
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
import com.spadatech.mobile.android.foodframer.adapters.PrepdayItemListAdapter;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.PrepDay;
import com.spadatech.mobile.android.foodframer.models.PrepDayItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/6/16.
 */
public class NewPrepdayDialogFragment extends DialogFragment{

    public final String TAG = getClass().getSimpleName();
    private OnCreatePrepdayClickListener mListener;
    private EditText mPrepdayName;
    private EditText mItemName;
    private EditText mItemNote;
    private List<PrepDayItem> mPrepdayItemList;
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
        mPrepdayItemList = new ArrayList<>();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), R.string.toast_enter_dish_name, Toast.LENGTH_SHORT).show();
                }else{

                    PrepDayItem prepDayItem = new PrepDayItem();
                    prepDayItem.setName(mItemName.getText().toString());
                    prepDayItem.setNotes(mItemNote.getText().toString());

                    mPrepdayItemList.add(prepDayItem);

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
                if(mPrepdayItemList.isEmpty()){
                    Toast.makeText(getActivity(), R.string.toast_add_one_dish, Toast.LENGTH_SHORT).show();
                }if(mPrepdayName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), R.string.toast_enter_prepday_name, Toast.LENGTH_SHORT).show();
                }else {
                    PrepDay prepDay = new PrepDay();
                    prepDay.setName(mPrepdayName.getText().toString());
                    prepDay.setWeekdayId(WeekdayHelper.get().getWeekday().getId());

                    ContentValues values = new ContentValues();
                    values.put(PrepDay.KEY_PREPDAY_NAME, prepDay.getName());
                    values.put(PrepDay.KEY_PREPDAY_WEEKDAY_ID, prepDay.getWeekdayId());

                    Uri uri = DatabaseHelper.PREPDAY_CONTENT_URI;
                    getActivity().getContentResolver().insert(uri, values);

                    // Try to query the newly created Prepday and get it's id
                    String whereClause = "PrepDayName = ? AND WeekdayId = ?";
                    String[] selectionArgs = {prepDay.getName(), prepDay.getWeekdayId()+""};
                    Cursor cursor = getActivity().getContentResolver().query(uri, null, whereClause, selectionArgs, null);
                    int prepDayId = 0;

                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                prepDayId = cursor.getInt(cursor.getColumnIndex(PrepDay.KEY_PREPDAY_ID));
                                prepDay.setId(prepDayId);
                            }
                        }
                    }

                    // Create new PrepDay Items
                    for(int i = 0; i < mPrepdayItemList.size(); i++){
                        ContentValues valuess = new ContentValues();
                        valuess.put(PrepDayItem.KEY_PREPDAY_ITEM_NAME, mPrepdayItemList.get(i).getName());
                        valuess.put(PrepDayItem.KEY_PREPDAY_ITEM_NOTES, mPrepdayItemList.get(i).getNotes());
                        valuess.put(PrepDayItem.KEY_PREPDAY_ITEM_PREPDAY_ID, prepDay.getId());

                        Uri urii = DatabaseHelper.PREPDAY_ITEM_CONTENT_URI;
                        getActivity().getContentResolver().insert(urii, valuess);
                    }

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

        mAdapter = new PrepdayItemListAdapter(mPrepdayItemList, true);
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

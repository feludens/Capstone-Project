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
import com.spadatech.mobile.android.foodframer.adapters.GroceryItemListAdapter;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.helpers.WeekdayHelper;
import com.spadatech.mobile.android.foodframer.models.Grocery;
import com.spadatech.mobile.android.foodframer.models.GroceryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/6/16.
 */
public class NewGroceryDialogFragment extends DialogFragment{

    public final String TAG = getClass().getSimpleName();
    private EditText mEditText;
    private List<GroceryItem> mGroceryItemList;
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
        Button mAddButton = (Button) v.findViewById(R.id.button_add_new_grocery);
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_new_groceries);
        mGroceryItemList = new ArrayList<>();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), R.string.toast_enter_a_value, Toast.LENGTH_SHORT).show();
                }else{

                    GroceryItem newItem = new GroceryItem();
                    newItem.setName(mEditText.getText().toString());
                    newItem.setChecked(0);

                    mGroceryItemList.add(newItem);

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

                if(!mGroceryItemList.isEmpty()) {
                    // Create new Grocery row
                    Grocery newGrocery = new Grocery();
                    newGrocery.setName("Grocery List");
                    newGrocery.setWeekdayId(WeekdayHelper.get().getWeekday().getId());

                    ContentValues values = new ContentValues();
                    values.put(Grocery.KEY_GROCERY_NAME, newGrocery.getName());
                    values.put(Grocery.KEY_GROCERY_WEEKDAY_ID, newGrocery.getWeekdayId());

                    Uri uri = DatabaseHelper.GROCERY_CONTENT_URI;
                    getActivity().getContentResolver().insert(uri, values);

                    // Try to query the newly created Grocery and get it's id
                    String whereClause = "GroceryName = ? AND WeekdayId = ?";
                    String[] selectionArgs = {newGrocery.getName(), newGrocery.getWeekdayId()+""};
                    Cursor cursor = getActivity().getContentResolver().query(uri, null, whereClause, selectionArgs, null);
                    int groceryId = 0;

                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                groceryId = cursor.getInt(cursor.getColumnIndex(Grocery.KEY_GROCERY_ID));
                                newGrocery.setId(groceryId);
                            }
                        }
                    }

                    // Create new Grocery Items
                    for(int i = 0; i < mGroceryItemList.size(); i++){
                        ContentValues valuess = new ContentValues();
                        valuess.put(GroceryItem.KEY_GROCERY_ITEM_NAME, mGroceryItemList.get(i).getName());
                        valuess.put(GroceryItem.KEY_GROCERY_ITEM_CHECKED, false);
                        valuess.put(GroceryItem.KEY_GROCERY_ITEM_GROCERY_ID, newGrocery.getId());

                        Uri urii = DatabaseHelper.GROCERY_ITEM_CONTENT_URI;
                        getActivity().getContentResolver().insert(urii, valuess);
                    }
                }
                else{
                    if(mEditText.getText().toString().isEmpty()){
                        Toast.makeText(getActivity(), "Add at least one item to the list.", Toast.LENGTH_LONG).show();
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

        mAdapter = new GroceryItemListAdapter(mGroceryItemList, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return alertDialogBuilder.show();
    }

}

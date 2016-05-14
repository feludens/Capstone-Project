package com.spadatech.mobile.android.foodframer.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.spadatech.mobile.android.foodframer.R;

import java.util.List;

/**
 * Created by Felipe S. Pereira on 5/6/16.
 */
public class NewGroceryDialogFragment extends DialogFragment{

    public final String TAG = getClass().getSimpleName();
    private OnCreateGroceryClickListener mListener;
//    private CheckBox mCheckbox;

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
        View v = inflater.inflate(R.layout.dialog_new_plan, null);
        mEditText = (EditText) v.findViewById(R.id.txt_your_name);
        mEditText.requestFocus();
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setTitle(R.string.new_plan);
        alertDialogBuilder.setPositiveButton(R.string.create,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onCreateGroceryClicked(mEditText.getText().toString());
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

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
        void onCreateGroceryClicked(List groceries);
    }
}

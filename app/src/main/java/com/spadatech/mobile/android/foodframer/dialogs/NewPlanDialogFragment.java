package com.spadatech.mobile.android.foodframer.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.spadatech.mobile.android.foodframer.R;

/**
 * Created by Felipe S. Pereira on 5/6/16.
 */
public class NewPlanDialogFragment extends DialogFragment{

    public final String TAG = getClass().getSimpleName();
    private EditText mEditText;

    public NewPlanDialogFragment() {
    }

    public static NewPlanDialogFragment newInstance() {
        NewPlanDialogFragment frag = new NewPlanDialogFragment();
        return frag;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        mEditText.requestFocus();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_new_plan, null);
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setTitle(R.string.new_plan);
        alertDialogBuilder.setPositiveButton(R.string.create,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                Log.d("Ludens", "It worked");
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
}

package com.spadatech.mobile.android.foodframer.helpers;

import android.content.Context;
import android.content.DialogInterface;

import java.util.regex.Pattern;

/**
 * Created by pereirf on 4/24/16.
 */
public class AlertHelper {

    public enum AlertType{
        ALERT_PASSWORD_MISMATCH,
        ALERT_PASSWORD_TOO_SHORT,
        ALERT_INVALID_EMAIL,
        ALERT_FIRST_NAME_MISSING,
        ALERT_LAST_NAME_MISSING,
        ALERT_SHORT_USERNAME
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static void showAlertDialog(Context context, String message)
    {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Ooopsie");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Got it",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.show();
    }
}

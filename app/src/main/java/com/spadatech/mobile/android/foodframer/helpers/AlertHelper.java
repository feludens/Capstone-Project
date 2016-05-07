package com.spadatech.mobile.android.foodframer.helpers;

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
}

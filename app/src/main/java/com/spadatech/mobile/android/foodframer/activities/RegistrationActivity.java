package com.spadatech.mobile.android.foodframer.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.helpers.AlertHelper;
import com.spadatech.mobile.android.foodframer.managers.SessionManager;
import com.spadatech.mobile.android.foodframer.models.User;

import io.realm.Realm;

/**
 * Created by Felipe S. Pereira
 */
public class RegistrationActivity extends AppCompatActivity {

    EditText mFirstName;
    EditText mLastName;
    EditText mUsername;
    EditText mEmail;
    EditText mPassword;
    EditText mConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_registration);

        mFirstName = (EditText) findViewById(R.id.et_first_name);
        mLastName = (EditText) findViewById(R.id.et_last_name);
        mUsername = (EditText) findViewById(R.id.et_username);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void registerNewUser(View v){
        if(validateUser()) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            User mNewUser = realm.createObject(User.class);
            mNewUser.setFirstName(mFirstName.getText().toString());
            mNewUser.setLastName(mLastName.getText().toString());
            mNewUser.setUsername(mUsername.getText().toString());
            mNewUser.setEmail(mEmail.getText().toString());
            mNewUser.setPassword(mPassword.getText().toString());
            realm.commitTransaction();

            SessionManager mSessionManager = new SessionManager(this);
            if(mSessionManager.createSession(mUsername.getText().toString(), mEmail.getText().toString())){
                Intent intent = new Intent(this, PlanListActivity.class);
                startActivity(intent);
            }
        }
    }

    private boolean validateUser(){
        if(mFirstName.getText().length() < 1){
            showAlert(AlertHelper.AlertType.ALERT_FIRST_NAME_MISSING);
            return false;
        }

        if(mLastName.getText().length() < 1){
            showAlert(AlertHelper.AlertType.ALERT_LAST_NAME_MISSING);
            return false;
        }

        if(mUsername.getText().length() < 6){
            showAlert(AlertHelper.AlertType.ALERT_SHORT_USERNAME);
            return false;
        }

        if(mEmail.getText().length() < 6){
            showAlert(AlertHelper.AlertType.ALERT_INVALID_EMAIL);
            return false;
        }else{
            if(!AlertHelper.EMAIL_ADDRESS_PATTERN.matcher(mEmail.getText()).matches()){
                showAlert(AlertHelper.AlertType.ALERT_INVALID_EMAIL);
                return false;
            }
        }

        String passwordText = mPassword.getText().toString();
        String confirmPasswordText = mConfirmPassword.getText().toString();

        if(passwordText.length() < 6){
            showAlert(AlertHelper.AlertType.ALERT_PASSWORD_TOO_SHORT);
            return false;
        }

        if(!passwordText.equals(confirmPasswordText)){
            showAlert(AlertHelper.AlertType.ALERT_PASSWORD_MISMATCH);
            return false;
        }

        return true;
    }

    private void showAlert(AlertHelper.AlertType alertType) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.oooopsie));
        alertDialog.setMessage("Alert message to be shown");

        switch (alertType){
            case ALERT_FIRST_NAME_MISSING:
                alertDialog.setMessage(getString(R.string.alert_first_name_missing));
                break;
            case ALERT_LAST_NAME_MISSING:
                alertDialog.setMessage(getString(R.string.alert_last_name_missing));
                break;
            case ALERT_INVALID_EMAIL:
                alertDialog.setMessage(getString(R.string.alert_invalid_email));
                break;
            case ALERT_SHORT_USERNAME:
                alertDialog.setMessage(getString(R.string.alert_username_too_short));
                break;
            case ALERT_PASSWORD_TOO_SHORT:
                alertDialog.setMessage(getString(R.string.alert_password_too_short));
                break;
            case ALERT_PASSWORD_MISMATCH:
                alertDialog.setMessage(getString(R.string.alert_passwords_do_not_match));
                break;
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.got_it),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}

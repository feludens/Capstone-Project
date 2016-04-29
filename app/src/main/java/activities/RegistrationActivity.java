package activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spadatech.mobile.android.foodframer.R;

import helpers.AlertHelper;
import io.realm.Realm;
import models.User;

public class RegistrationActivity extends AppCompatActivity {

    private User mNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_registration);

        Realm realm = Realm.getDefaultInstance();
        mNewUser = realm.createObject(User.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void registerNewUser(View v){
        if(validateUser()) {

        }
    }

    private boolean validateUser(){
        EditText firstName = (EditText) findViewById(R.id.et_first_name);
        EditText lastName = (EditText) findViewById(R.id.et_last_name);
        EditText username = (EditText) findViewById(R.id.et_username);
        EditText email = (EditText) findViewById(R.id.et_email);
        EditText password = (EditText) findViewById(R.id.et_password);
        EditText confirmPassword = (EditText) findViewById(R.id.et_confirm_password);

        if(firstName.getText().length() < 1){
            showAlert(AlertHelper.AlertType.ALERT_FIRST_NAME_MISSING);
            return false;
        }
        mNewUser.setUserFirstName(firstName.getText().toString());

        if(lastName.getText().length() < 1){
            showAlert(AlertHelper.AlertType.ALERT_LAST_NAME_MISSING);
            return false;
        }
        mNewUser.setUserLastName(lastName.getText().toString());

        if(username.getText().length() < 6){
            showAlert(AlertHelper.AlertType.ALERT_SHORT_USERNAME);
            return false;
        }
        mNewUser.setUsername(username.getText().toString());

        if(email.getText().length() < 6){
            showAlert(AlertHelper.AlertType.ALERT_INVALID_EMAIL);
            return false;
        }else{
            if(!AlertHelper.EMAIL_ADDRESS_PATTERN.matcher(email.getText()).matches()){
                showAlert(AlertHelper.AlertType.ALERT_INVALID_EMAIL);
                return false;
            }
        }
        mNewUser.setEmail(email.getText().toString());

        String passwordText = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();

        if(passwordText.length() < 6){
            showAlert(AlertHelper.AlertType.ALERT_PASSWORD_TOO_SHORT);
            return false;
        }

        if(!passwordText.equals(confirmPasswordText)){
            showAlert(AlertHelper.AlertType.ALERT_PASSWORD_MISMATCH);
            return false;
        }
        mNewUser.setPassword(passwordText);

        return true;
    }

    private void showAlert(AlertHelper.AlertType alertType) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Ooopsie!");
        alertDialog.setMessage("Alert message to be shown");

        switch (alertType){
            case ALERT_FIRST_NAME_MISSING:
                alertDialog.setMessage("You forgot your first name.");
                break;
            case ALERT_LAST_NAME_MISSING:
                alertDialog.setMessage("You forgot your last name.");
                break;
            case ALERT_INVALID_EMAIL:
                alertDialog.setMessage("You entered an invalid email.");
                break;
            case ALERT_SHORT_USERNAME:
                alertDialog.setMessage("Username must be at least six characters long.");
                break;
            case ALERT_PASSWORD_TOO_SHORT:
                alertDialog.setMessage("Password must be at least six characters long.");
                break;
            case ALERT_PASSWORD_MISMATCH:
                alertDialog.setMessage("Passwords do not match.");
                break;
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Got it!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}

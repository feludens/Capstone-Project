package com.spadatech.mobile.android.foodframer.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spadatech.mobile.android.foodframer.R;

import io.realm.Realm;
import io.realm.RealmResults;
import com.spadatech.mobile.android.foodframer.managers.SessionManager;
import com.spadatech.mobile.android.foodframer.models.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);
    }

    public void login(View v){
        EditText username = (EditText) findViewById(R.id.et_login_username);
        EditText password = (EditText) findViewById(R.id.et_login_password);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> result = realm.where(User.class)
                .equalTo("username", username.getText().toString())
                .or()
                .equalTo("email", username.getText().toString())
                .findAll();

        if(result.size() > 0 && result.first().getPassword().equals(password.getText().toString())){
            SessionManager sessionManager = new SessionManager(this);
            if(sessionManager.createSession(result.first().getUsername(), result.first().getEmail())){
                Intent intent = new Intent(this, PlanListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Ooopsie!");
            alertDialog.setMessage("You enter the wrong credentials. Try again!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Got it!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    public void createAccount(View v){
        Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registrationIntent);
    }
}

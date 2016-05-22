package com.spadatech.mobile.android.foodframer.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.dbtables.UserTable;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.managers.SessionManager;
import com.spadatech.mobile.android.foodframer.models.User;

/**
 * Created by Felipe S. Pereira on 4/30/16.
 */
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

        UserTable userTable = new UserTable();
        User user = new User();
        Cursor cursor = null;

        if(password != null) {
            if (username != null) {
                if (!username.getText().toString().isEmpty()) {
                    String whereClause;
                    String[] selectionArgs = new String[2];
                    if (username.getText().toString().contains("@")) {
                        whereClause = "email = ? AND password = ?";
                        selectionArgs[0] = username.getText().toString();
                        selectionArgs[1] = password.getText().toString();
                    }else{
                        whereClause = "username = ? AND password = ?";
                        selectionArgs[0] = username.getText().toString();
                        selectionArgs[1] = password.getText().toString();
                    }

                    Uri uri = DatabaseHelper.USER_CONTENT_URI;
                    cursor = getContentResolver().query(uri, null, whereClause, selectionArgs, null);
                }
            }
        }

        if(cursor != null) {
            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    user.setUsername(cursor.getString(cursor.getColumnIndex(User.KEY_USER_USERNAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(User.KEY_USER_EMAIL)));
                    SessionManager sessionManager = new SessionManager(this);
                    if (sessionManager.createSession(user.getUsername(), user.getEmail())) {
                        Intent intent = new Intent(this, PlanListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }else{
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(getString(R.string.oooopsie));
                alertDialog.setMessage(getString(R.string.alert_wrong_credentials));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.got_it),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.oooopsie));
            alertDialog.setMessage(getString(R.string.alert_wrong_credentials));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.got_it),
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

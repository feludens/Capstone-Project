package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.spadatech.mobile.android.foodframer.R;

import io.realm.Realm;
import io.realm.RealmResults;
import models.User;

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

        Log.d("Ludens", "username: " + username.getText().toString());
        Log.d("Ludens", "password: " + password.getText().toString());

        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> result = realm.where(User.class)
                .equalTo("username", username.getText().toString())
                .or()
                .equalTo("email", username.getText().toString())
                .findAll();

        if(result.first().getPassword().equals(password.getText().toString())){
            Log.d("Ludens", "SUCCESS");
        }

    }

    public void createAccount(View v){
        Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registrationIntent);
    }
}

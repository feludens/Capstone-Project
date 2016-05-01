package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.spadatech.mobile.android.foodframer.R;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);
        Realm realm = Realm.getDefaultInstance();
        Log.d("Ludens", "path: " + realm.getPath());
    }

    public void login(View v){

    }

    public void createAccount(View v){
        Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registrationIntent);
    }
}

package com.spadatech.mobile.android.foodframer.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.spadatech.mobile.android.foodframer.R;

import com.spadatech.mobile.android.foodframer.managers.SessionManager;

/**
 * Created by Felipe S. Pereira
 */
public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SessionManager mSessionManager = new SessionManager(SplashScreenActivity.this);
                mSessionManager.validateSessionAndNavigate(SplashScreenActivity.this);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }
}

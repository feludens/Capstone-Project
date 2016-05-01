package activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.spadatech.mobile.android.foodframer.R;

import managers.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash_screen);

//        final Context context = this;

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SessionManager mSessionManager = new SessionManager(SplashScreenActivity.this);
                mSessionManager.validateSessionAndNavigate();
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }
}

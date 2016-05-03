package managers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import activities.LoginActivity;
import activities.PlanListActivity;

/**
 * Created by Felipe S. Pereira on 4/30/16.
 */
public class SessionManager {

    private static final String PREFS_NAME = "FoodFramerPrefs";
    private static final String IS_USER_LOGIN = "loggedin";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";

    private SharedPreferences mPrefs;
    private Context mContext;
    private int PRIVATE_MODE = 0;

    public SessionManager(Context context){
        this.mContext = context;
        mPrefs = mContext.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
    }

    //Create login session
    public boolean createSession(String username, String email){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        return editor.commit();
    }

    public void validateSessionAndNavigate(){
        if(isUserLoggedIn()){
            Intent intent = new Intent(mContext, PlanListActivity.class);
            mContext.startActivity(intent);
        }else{
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }
    }

    public HashMap<String, String> getUserInfo(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USERNAME, mPrefs.getString(KEY_USERNAME, null));
        user.put(KEY_EMAIL, mPrefs.getString(KEY_EMAIL, null));

        return user;
    }

    public void logout(){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }

    public boolean isUserLoggedIn(){
        return mPrefs != null ? mPrefs.getBoolean(IS_USER_LOGIN, false) : false;
    }
}
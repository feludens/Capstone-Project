package com.spadatech.mobile.android.foodframer.managers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.spadatech.mobile.android.foodframer.activities.LoginActivity;
import com.spadatech.mobile.android.foodframer.activities.PlanListActivity;

import java.util.HashMap;

/**
 * Created by Felipe S. Pereira on 4/30/16.
 */
public class SessionManager {

    private static SessionManager instance;

    public synchronized static SessionManager get(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    private static final String PREFS_NAME = "FoodFramerPrefs";
    private static final String IS_USER_LOGIN = "loggedin";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";

    private SharedPreferences mPrefs;
    private int PRIVATE_MODE = 0;

    public SessionManager(Context context){
        mPrefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
    }

    //Create login session
    public boolean createSession(String username, String email){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        return editor.commit();
    }

    public void validateSessionAndNavigate(Context context){
        if(isUserLoggedIn()){
            Intent intent = new Intent(context, PlanListActivity.class);
            context.startActivity(intent);
        }else{
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }

    public HashMap<String, String> getUserInfo(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USERNAME, mPrefs.getString(KEY_USERNAME, null));
        user.put(KEY_EMAIL, mPrefs.getString(KEY_EMAIL, null));

        return user;
    }

    public void logout(Context context){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public boolean isUserLoggedIn(){
        return mPrefs != null ? mPrefs.getBoolean(IS_USER_LOGIN, false) : false;
    }
}
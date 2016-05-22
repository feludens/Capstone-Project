package com.spadatech.mobile.android.foodframer;

import android.app.Application;
import android.content.Context;

import com.spadatech.mobile.android.foodframer.helpers.DBHelper;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class App extends Application {

    private static Context context;
    private static DBHelper dbHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
//        DatabaseManager.initializeInstance(dbHelper);
    }

    public static Context getContext(){
        return context;
    }
}

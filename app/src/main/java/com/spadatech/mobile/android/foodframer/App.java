package com.spadatech.mobile.android.foodframer;

import android.app.Application;
import android.content.Context;

import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class App extends Application {

    private static Context context;
    private static DatabaseHelper databaseHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        databaseHelper = new DatabaseHelper();
//        DatabaseManager.initializeInstance(dbHelper);
    }

    public static Context getContext(){
        return context;
    }
}

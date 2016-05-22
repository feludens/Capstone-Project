package com.spadatech.mobile.android.foodframer.helpers;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spadatech.mobile.android.foodframer.App;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class DBHelper extends SQLiteOpenHelper{

    // DB Version
    private static final int DATABASE_VERSION = 1;
    // DB Name
    private static final String DATABASE_NAME = "sqliteFoodFramer.db";

    public DBHelper( ) {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

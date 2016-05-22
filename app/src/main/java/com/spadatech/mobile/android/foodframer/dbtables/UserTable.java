package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.spadatech.mobile.android.foodframer.managers.DatabaseManager;
import com.spadatech.mobile.android.foodframer.models.User;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class UserTable {

    private User mUser;

    public UserTable(){
        mUser = new User();
    }

    public static String createTable(){
        return "CREATE TABLE " + User.TABLE  + "("
                + User.KEY_USER_USERNAME  + " TEXT PRIMARY KEY,"
                + User.KEY_USER_FIRST_NAME  + " TEXT,"
                + User.KEY_USER_LAST_NAME  + " TEXT,"
                + User.KEY_USER_EMAIL  + " TEXT,"
                + User.KEY_USER_PASSWORD + " TEXT )";
    }

    public void insert(User user) {
//        int courseId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_USER_USERNAME, user.getUsername());
        values.put(User.KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(User.KEY_USER_LAST_NAME, user.getLastName());
        values.put(User.KEY_USER_EMAIL, user.getEmail());
        values.put(User.KEY_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(User.TABLE, null, values);
//        courseId = (int)db.insert(Course.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
//
//        return courseId;
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(User.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}

package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;

import com.spadatech.mobile.android.foodframer.App;
import com.spadatech.mobile.android.foodframer.helpers.DatabaseHelper;
import com.spadatech.mobile.android.foodframer.models.User;

/**
 * Created by Felipe S. Pereira on 5/21/16.
 */
public class UserTable {

    private User mUser;
    private DatabaseHelper mDatabaseHelper;

    public UserTable(){
        mUser = new User();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + User.TABLE  + "("
                + User.KEY_USER_USERNAME  + " TEXT PRIMARY KEY,"
                + User.KEY_USER_FIRST_NAME  + " TEXT,"
                + User.KEY_USER_LAST_NAME  + " TEXT,"
                + User.KEY_USER_EMAIL  + " TEXT,"
                + User.KEY_USER_PASSWORD + " TEXT )";
    }

    public void insert(User user) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        ContentValues values = new ContentValues();
        values.put(User.KEY_USER_USERNAME, user.getUsername());
        values.put(User.KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(User.KEY_USER_LAST_NAME, user.getLastName());
        values.put(User.KEY_USER_EMAIL, user.getEmail());
        values.put(User.KEY_USER_PASSWORD, user.getPassword());

        // Inserting Row
        mDatabaseHelper.getDatabase().insert(User.TABLE, null, values);
        mDatabaseHelper.close();
    }

    public void delete( ) {
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        mDatabaseHelper.getDatabase().delete(User.TABLE, null, null);
        mDatabaseHelper.close();
    }

    public User findUserByEmailAndPassword(String email, String password){
        User user = null;
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + User.TABLE + " Where " + User.KEY_USER_EMAIL + "=" + email + " AND " + User.KEY_USER_PASSWORD + "=" + password;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User userResult = new User();
                userResult.setEmail(email);
                userResult.setPassword(password);
                userResult.setFirstName(cursor.getString(cursor.getColumnIndex(User.KEY_USER_FIRST_NAME)));
                userResult.setLastName(cursor.getString(cursor.getColumnIndex(User.KEY_USER_LAST_NAME)));
                userResult.setUsername(cursor.getString(cursor.getColumnIndex(User.KEY_USER_USERNAME)));

                user = userResult;
            } while (cursor.moveToNext());
        }

        cursor.close();
        mDatabaseHelper.close();

        return user;
    }

    public User findUserByUsernameAndPassword(String username, String password){
        User user = null;
        mDatabaseHelper = new DatabaseHelper(App.getContext());
        mDatabaseHelper.open();
        String query = " SELECT * from " + User.TABLE + " Where " + User.KEY_USER_USERNAME + "=" + username + " AND " + User.KEY_USER_PASSWORD + "=" + password;

        Cursor cursor = mDatabaseHelper.getDatabase().rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User userResult = new User();
                userResult.setUsername(username);
                userResult.setPassword(password);
                userResult.setFirstName(cursor.getString(cursor.getColumnIndex(User.KEY_USER_FIRST_NAME)));
                userResult.setLastName(cursor.getString(cursor.getColumnIndex(User.KEY_USER_LAST_NAME)));
                userResult.setEmail(cursor.getString(cursor.getColumnIndex(User.KEY_USER_EMAIL)));

                user = userResult;
            } while (cursor.moveToNext());
        }

        cursor.close();
        mDatabaseHelper.close();

        return user;
    }
}

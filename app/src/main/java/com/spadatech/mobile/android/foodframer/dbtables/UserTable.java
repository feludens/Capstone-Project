package com.spadatech.mobile.android.foodframer.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
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
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_USER_USERNAME, user.getUsername());
        values.put(User.KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(User.KEY_USER_LAST_NAME, user.getLastName());
        values.put(User.KEY_USER_EMAIL, user.getEmail());
        values.put(User.KEY_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(User.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(User.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public User findUserByEmailAndPassword(String email, String password){
        User user = null;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + User.TABLE + " Where " + User.KEY_USER_EMAIL + "=" + email + " AND " + User.KEY_USER_PASSWORD + "=" + password;

        Cursor cursor = db.rawQuery(query, null);
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
        DatabaseManager.getInstance().closeDatabase();

        return user;
    }

    public User findUserByUsernameAndPassword(String username, String password){
        User user = null;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = " SELECT * from " + User.TABLE + " Where " + User.KEY_USER_USERNAME + "=" + username + " AND " + User.KEY_USER_PASSWORD + "=" + password;

        Cursor cursor = db.rawQuery(query, null);
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
        DatabaseManager.getInstance().closeDatabase();

        return user;
    }
}

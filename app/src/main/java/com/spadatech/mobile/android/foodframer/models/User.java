package com.spadatech.mobile.android.foodframer.models;

/**
 * Created by Felipe S. Pereira on 4/23/16.
 */
public class User {

    // Table name
    public static final String TABLE = "Users";

    // Table Columns
    public static final String KEY_USER_EMAIL = "Email";
    public static final String KEY_USER_FIRST_NAME = "FirstName";
    public static final String KEY_USER_LAST_NAME = "LastName";
    public static final String KEY_USER_USERNAME = "Username";
    public static final String KEY_USER_PASSWORD = "Password";

    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

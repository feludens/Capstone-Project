package com.spadatech.mobile.android.foodframer.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/23/16.
 */
public class User extends RealmObject {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private RealmList<Plan> plans;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String userFirstName) {
        this.firstname = userFirstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String userLastName) {
        this.lastname = userLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RealmList<Plan> getPlanList() {
        return plans;
    }

    public void setPlanList(RealmList<Plan> planList) {
        this.plans = planList;
    }
}

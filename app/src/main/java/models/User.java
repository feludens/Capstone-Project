package models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Felipe S. Pereira on 4/23/16.
 */
public class User extends RealmObject {

    private String mUsername;
    private String mUserFirstName;
    private String mUserLastName;
    private String mEmail;
    private String mPassword;
    private RealmList<Plan> mPlanList;

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getUserFirstName() {
        return mUserFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.mUserFirstName = userFirstName;
    }

    public String getUserLastName() {
        return mUserLastName;
    }

    public void setUserLastName(String userLastName) {
        this.mUserLastName = userLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public RealmList<Plan> getPlanList() {
        return mPlanList;
    }

    public void setPlanList(RealmList<Plan> planList) {
        this.mPlanList = planList;
    }
}

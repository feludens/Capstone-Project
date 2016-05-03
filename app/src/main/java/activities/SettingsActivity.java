package activities;


import android.os.Bundle;
import android.preference.PreferenceActivity;

import fragments.BasePreferenceFragment;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new BasePreferenceFragment()).commit();
    }
}

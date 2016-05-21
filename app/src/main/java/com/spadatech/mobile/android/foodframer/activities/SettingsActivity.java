package com.spadatech.mobile.android.foodframer.activities;


import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.spadatech.mobile.android.foodframer.fragments.BasePreferenceFragment;

/**
 * Created by Felipe S. Pereira
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new BasePreferenceFragment()).commit();
    }
}

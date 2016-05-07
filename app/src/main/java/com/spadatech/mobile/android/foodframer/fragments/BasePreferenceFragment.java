package com.spadatech.mobile.android.foodframer.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.spadatech.mobile.android.foodframer.R;

import com.spadatech.mobile.android.foodframer.managers.SessionManager;

/**
 * Created by pereirf on 5/2/16.
 */
public class BasePreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Can retrieve arguments from preference XML.


        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref);


        Preference button = findPreference(getString(R.string.button_logout_key));
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SessionManager manager = new SessionManager(getActivity());
                manager.logout();
                return true;
            }
        });
    }
}

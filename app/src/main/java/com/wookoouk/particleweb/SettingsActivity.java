package com.wookoouk.particleweb;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity {

    public static final String NAMESPACE = "com.wookoouk.particleweb";

    public static final String bgColorKey = "bgColor";
    public static final String pointColorKey = "pointColor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(NAMESPACE, MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        addPreferencesFromResource(R.xml.settings);


        final ListPreference bgColor = (ListPreference) findPreference(bgColorKey);
        bgColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                editor.putString(bgColorKey, newValue.toString());
                editor.apply();
                return false;
            }
        });

        final ListPreference pointColor = (ListPreference) findPreference(pointColorKey);
        pointColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                editor.putString(pointColorKey, newValue.toString());
                editor.apply();
                return false;
            }
        });
    }

}

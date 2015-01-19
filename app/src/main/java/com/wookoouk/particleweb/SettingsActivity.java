package com.wookoouk.particleweb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("ParticleWeb", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        addPreferencesFromResource(R.xml.settings);


        final ListPreference bg = (ListPreference) findPreference("bgColor");






        bg.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                editor.putString("bg", newValue.toString());
                editor.apply();
                return false;
            }
        });
    }


}

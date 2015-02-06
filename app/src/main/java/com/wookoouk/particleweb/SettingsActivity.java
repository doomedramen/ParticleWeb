package com.wookoouk.particleweb;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

public class SettingsActivity extends Activity {


    public static final String NAMESPACE = "com.wookoouk.particleweb";

    public static final String bgColorKey = "bgColor";
    public static final String pointColorKey = "pointColor";
    public static String showPoints = "showPoints";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
    }
}

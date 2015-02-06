package com.wookoouk.particleweb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.ListAdapter;


public class ColorList extends ListPreference {
    public ColorList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {

        ListAdapter listAdapter = new ColorListAdapter(getContext(),
                R.layout.themelist, this.getEntryValues(), this);

        builder.setAdapter(listAdapter, this);
        super.onPrepareDialogBuilder(builder);
    }
}

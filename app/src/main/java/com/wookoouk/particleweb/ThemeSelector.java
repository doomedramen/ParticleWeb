package com.wookoouk.particleweb;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.widget.ListAdapter;

public class ThemeSelector extends ListPreference
{

    public ThemeSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        int index = findIndexOfValue(getSharedPreferences().getString(
                getKey(), "1"));

        ListAdapter listAdapter = (ListAdapter) new ThemeArrayAdapter(getContext(),
                R.layout.themelist, this.getEntryValues() ,index, this);

        builder.setAdapter(listAdapter, this);
        super.onPrepareDialogBuilder(builder);
    }

    public void SetResult(int id) {
    }
}
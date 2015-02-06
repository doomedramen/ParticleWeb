package com.wookoouk.particleweb;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ColorListAdapter extends ArrayAdapter<CharSequence> implements View.OnClickListener {
    ColorList cl;
    SharedPreferences preferences;

    public ColorListAdapter(Context context, int textViewResourceId, CharSequence[] objects, ColorList colorList) {
        super(context, textViewResourceId, objects);
        cl = colorList;
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String colorHex = this.getItem(position).toString();
        int color = Color.parseColor(colorHex);

        //inflate layout
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View row = inflater.inflate(R.layout.themelist, parent, false);

        View view = row.findViewById(R.id.bk);
        view.setBackgroundColor(color);
        view.setOnClickListener(this);


        return row;
    }

    @Override
    public void onClick(View v) {

        ColorDrawable bgColor = (ColorDrawable) v.getBackground();
        String hexColor = "#" + Integer.toHexString(bgColor.getColor()).substring(2);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(cl.getKey(), hexColor);
        editor.apply();
        System.out.println(cl.getKey() + " : " + hexColor);
        cl.getDialog().dismiss();
    }

}

package com.wookoouk.particleweb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by pagem on 21/01/15.
 */
public class ThemeArrayAdapter extends ArrayAdapter<CharSequence> implements View.OnClickListener {
    int index;
    ThemeSelector ts;

    public ThemeArrayAdapter(Context context, int textViewResourceId, CharSequence[] objects, int selected, ThemeSelector ts) {
        super(context, textViewResourceId, objects);
        index = selected;
        this.ts = ts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get themeId
        CharSequence themeId = this.getItem(position);

        //get colors
        ThemeManager tm = new ThemeManager();
        int[] colors = tm.getColors(themeId.toString(), this.getContext().getResources());

        //inflate layout
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        View row = inflater.inflate(R.layout.themelist, parent, false);
        row.setId(Integer.parseInt(themeId.toString()));

        //set on click listener for row
        row.setOnClickListener(this);

        //set name
        TextView tv = (TextView) row.findViewById(R.id.themename);
        tv.setText(tm.getName(themeId.toString(), this.getContext().getResources()));
        tv.setTextColor(0xff000000);

        //set checkbox
        RadioButton tb = (RadioButton) row.findViewById(R.id.ckbox);
        if (position == index) {
            tb.setChecked(true);
        }
        tb.setClickable(false);

        //set colors
        View view = row.findViewById(R.id.bk);
        view.setBackgroundColor(colors[4]);
        view = row.findViewById(R.id.ant);
        view.setBackgroundColor(colors[0]);
        view = row.findViewById(R.id.antfood);
        view.setBackgroundColor(colors[1]);
        view = row.findViewById(R.id.food);
        view.setBackgroundColor(colors[2]);
        view = row.findViewById(R.id.ph);
        view.setBackgroundColor(colors[3]);
        view = row.findViewById(R.id.baseH);
        view.setBackgroundColor(colors[5]);
        view = row.findViewById(R.id.baseL);
        view.setBackgroundColor(colors[6]);

        return row;
    }

    @Override
    public void onClick(View v)
    {
        ts.SetResult(v.getId());
    }
}
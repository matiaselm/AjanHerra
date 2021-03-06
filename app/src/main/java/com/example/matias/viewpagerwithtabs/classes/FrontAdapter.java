package com.example.matias.viewpagerwithtabs.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matias.viewpagerwithtabs.R;

import java.util.List;

/**
 * This is the custom listview adapter that has 2 different view positions.
 */

public class FrontAdapter extends ArrayAdapter<String> {

    private int resourceLayout;
    private Context mContext;

    public FrontAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    /**
     * Finds the String arraylist item and prints the strings.
     * 1. position: extract String after /
     * 2. position: extract String before /
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        String p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.actionRow);
            TextView tt2 = (TextView) v.findViewById(R.id.timeRow);

            //Separate what is before and after "/" in the string feed

            if (tt1 != null) {
                tt1.setText(p.replaceAll(".*/", ""));
            }

            if (tt2 != null) {
                tt2.setText(p.replaceAll("\\/.*", ""));
            }
        }

        return v;
    }

}
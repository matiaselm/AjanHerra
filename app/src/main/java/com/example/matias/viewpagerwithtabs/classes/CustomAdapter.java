package com.example.matias.viewpagerwithtabs.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matias.viewpagerwithtabs.R;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Action> {

    private int resourceLayout;
    private Context mContext;

    public CustomAdapter(Context context, int resource, List<Action> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Action p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.type);
            TextView tt2 = (TextView) v.findViewById(R.id.reference);
            TextView tt3 = (TextView) v.findViewById(R.id.average);
            TextView tt4 = (TextView) v.findViewById(R.id.result);

            if (tt1 != null) {
                tt1.setText(p.getType());
            }

            if (tt2 != null) {
                tt2.setText(p.getTimeReference());
            }

            if (tt3 != null) {
                tt3.setText("    " + p.getTimeAverage());
            }

            if (tt4 != null) {
                tt4.setText(p.getTimeResult());
            }
        }

        return v;
    }

}
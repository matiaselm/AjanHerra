package com.example.matias.viewpagerwithtabs;

import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

import com.example.matias.viewpagerwithtabs.R;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {
    private int layout;
    private Context context;
    private ArrayList<String> datas;
    public MyAdapter(Context context, int layout,
                     ArrayList<String> datas) {
        super(context, layout, datas);
        this.layout = layout;
        this.context = context;
        this.datas = datas;
    }
  /**  @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        Log.d("QWERTY", "getView(" + position + ")");
        View view = convertView;
        if(view == null) {
            LayoutInflater inf =
                    (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLAT
                            ER_SERVICE);
            view = inf.inflate(layout, null);
        }

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        tv1.setText(datas.get(position));

        tv2.setText(Integer.toString(datas.get(position).length()));
        return view;*/
    }
}
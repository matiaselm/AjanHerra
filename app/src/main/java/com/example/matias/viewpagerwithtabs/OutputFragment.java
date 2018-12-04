package com.example.matias.viewpagerwithtabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OutputFragment extends Fragment {


    public OutputFragment() {
        // Required empty public constructor
    }

    View t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        t = inflater.inflate(R.layout.fragment_output, container, false);

        return t;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("frag", "onResume output");

        ArrayList list = ActionList.getInstance().getActivities();

        TextView textView = t.findViewById(R.id.ActivityList);
        textView.setText("");
        textView.append("Aktiviteetti\n");
        textView.append("\n");

        for (int i = 0; i < list.size(); i++) {

            textView.append(ActionList.getInstance().getActivities().get(i).getType());
            textView.append("\n");

        }

        TextView textView2 = t.findViewById(R.id.HourList);
        textView2.setText("");
        textView2.append(" Ref   |  Avg  |  Res\n");
        textView2.append("\n");

        for (int i = 0; i < list.size(); i++) {

            textView2.append(ActionList.getInstance().getActivities().get(i).getInfo());
            textView2.append("\n");

        }



    }

}

package com.example.matias.viewpagerwithtabs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OutputFragment extends Fragment {

    private CustomAdapter customAdapter;

    public OutputFragment() {
        // Required empty public constructor
    }

    private ArrayList<String> myDataItems = new ArrayList<>();

    private static MainActivity thisActivity;

    View t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        t = inflater.inflate(R.layout.fragment_output, container, false);

        ArrayList list = ActionList.getInstance().getActivities();


        ListView yourListView = (ListView) t.findViewById(R.id.ActivityList);

        // get data from the table by the ListAdapter
        customAdapter = new CustomAdapter(getActivity(), R.layout.list_items, list);

        yourListView .setAdapter(customAdapter);

        return t;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("frag", "onResume output");

        customAdapter.notifyDataSetChanged();


        /*

        TextView textView = t.findViewById(R.id.ActivityList);
        textView.setText("");
        textView.append("Aktiviteetti\n");
        textView.append("\n");

        for (int i = 0; i < list.size(); i++) {

            textView.append(ActionList.getInstance().getActivities().get(i).getType());
            textView.append("\n");

        }

        TextView textView2 = t.findViewById(R.id.hourList);
        textView2.setText("");
        textView2.append(" Ref   |  Avg  |  Res\n");
        textView2.append("\n");

        for (int i = 0; i < list.size(); i++) {

            textView2.append(ActionList.getInstance().getActivities().get(i).getInfo());
            textView2.append("\n");

        }*/
    }

    public void refreshAdapter() {
       customAdapter.notifyDataSetChanged();
    }
}

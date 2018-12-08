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


    View t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        t = inflater.inflate(R.layout.fragment_output, container, false);

        ArrayList list = ActionList.getInstance().getActivities();


        ListView lv = (ListView) t.findViewById(R.id.ActivityList);

        // get data from the table by the ListAdapter
        customAdapter = new CustomAdapter(getActivity(), R.layout.list_items, list);

        lv.setAdapter(customAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Sovellus", i + " painettu");
                Intent nextActivity = new Intent(getActivity(), InfoActivity.class);
                nextActivity.putExtra("itempos", i);
                startActivity(nextActivity);
            }
        });

        return t;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("frag", "onResume output");

        customAdapter.notifyDataSetChanged();
    }

    public void refreshAdapter() {
       customAdapter.notifyDataSetChanged();
    }
}

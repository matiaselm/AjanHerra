package com.example.matias.viewpagerwithtabs.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.matias.viewpagerwithtabs.R;
import com.example.matias.viewpagerwithtabs.activities.InfoActivity;
import com.example.matias.viewpagerwithtabs.classes.Action;
import com.example.matias.viewpagerwithtabs.classes.CustomAdapter;
import com.example.matias.viewpagerwithtabs.singletons.ActionList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OutputFragment extends Fragment {

    private CustomAdapter customAdapter;
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    ArrayList actionList;

    public OutputFragment() {
        // Required empty public constructor
    }

    //private ArrayList<String> myDataItems = new ArrayList<>();

    View t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        t = inflater.inflate(R.layout.fragment_output, container, false);

        actionList = ActionList.getInstance().getActivities();

        ListView lv = (ListView) t.findViewById(R.id.ActivityList);

        // get data from the table by the ListAdapter
        customAdapter = new CustomAdapter(getActivity(), R.layout.list_items, actionList);

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

        Log.d("Sovellus", "onResume output");

        customAdapter.notifyDataSetChanged();
    }


   public void onPause(){
        super.onPause();
        Log.d("Sovellus", "OutputFragment onPause");
    }

    public void refreshAdapter() {
       customAdapter.notifyDataSetChanged();
    }
}

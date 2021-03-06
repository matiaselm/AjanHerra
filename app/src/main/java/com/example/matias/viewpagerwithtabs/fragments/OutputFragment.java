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
import android.view.inputmethod.InputMethodManager;
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
 * Output fragment sorts all the data from each action and loads it into a listview with CustomAdapter.
 * * Finds the Action class arraylist item and gets the parameters from it.
 * 1. position : Type
 * 2. position : Reference time
 * 3. position : Average hours as listview format
 * 4. position : Resulting time suggestions
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

    /**
     * onCreateView we create view we udate the action list and send it to customAdapter.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

        /**
         * This listener gets the position of clicked listview element and moves us to that corresponding actions info view activity (InfoActivity).
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        closeKeyboard();
        customAdapter.notifyDataSetChanged();
    }

    public void onPause() {
        super.onPause();
        Log.d("Sovellus", "OutputFragment onPause");
    }

    public void closeKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    public void refreshAdapter() {
        customAdapter.notifyDataSetChanged();
    }
}

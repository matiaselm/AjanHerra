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
import com.example.matias.viewpagerwithtabs.classes.CustomAdapter;
import com.example.matias.viewpagerwithtabs.singletons.ActionList;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrontFragment extends Fragment {

    private CustomAdapter customAdapter;
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    ArrayList historyList;

    public FrontFragment() {
        // Required empty public constructor
    }

    View f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        f = inflater.inflate(R.layout.fragment_front, container, false);

        historyList = ActionList.getInstance().getActivities();

        ListView lv = (ListView) f.findViewById(R.id.historyList);

        // get data from the table by the ListAdapter
        customAdapter = new CustomAdapter(getActivity(), R.layout.historylist_items, historyList);

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

        return f;

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("Sovellus", "onResume output");
        //Do not know why this crash???
       closeKeyboard();
    }

    public void closeKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) { }
    }
}
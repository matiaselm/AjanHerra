package com.example.matias.viewpagerwithtabs.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matias.viewpagerwithtabs.R;
import com.example.matias.viewpagerwithtabs.activities.InfoActivity;
import com.example.matias.viewpagerwithtabs.classes.FrontAdapter;
import com.example.matias.viewpagerwithtabs.singletons.UserList;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrontFragment extends Fragment {

    private FrontAdapter customAdapter;
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    List historyList;

    public FrontFragment() {
        // Required empty public constructor
    }

    View f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        f = inflater.inflate(R.layout.fragment_front, container, false);

        historyList = UserList.getInstance().getCurrentUser().getHistoryList();

            updateList();

        //Do not know why this crash???
        closeKeyboard();

        f.findViewById(R.id.buttonHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Sovellus", "Pressed update front list");

                historyList = UserList.getInstance().getCurrentUser().getHistoryList();

                if (historyList.size() == 0) {
                    Toast.makeText(getContext(), "Ei historiatietoja",
                            Toast.LENGTH_SHORT).show();
                } else {
                    updateList();
                }
            }
        });

        return f;

    }

    /**
     * Get history list from current user. Feed it to list adapter.
     */
    public void updateList() {
            // get data from the table by the ListAdapter
        Log.d("Sovellus", "Update front list");
            customAdapter = new FrontAdapter(getActivity(), R.layout.historylist_items, historyList);

       /* for (int i = 0 ;i < historyList.size(); i++){
            String file = UserList.getInstance().getCurrentUser().getHistoryFile(i);
            Log.d ("Sovellus", "Front fragment found: " + file);
        }*/

            ListView lv = (ListView) f.findViewById(R.id.lvHistory);

            lv.setAdapter(customAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("Sovellus", "onResume Front");

        updateList();
    }

    public void closeKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }
}
package com.example.matias.viewpagerwithtabs;


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
    Gson listGson;
    String json;

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

        pref = t.getContext().getSharedPreferences("list", Context.MODE_PRIVATE);
        prefEditor = pref.edit();

        ListView lv = (ListView) t.findViewById(R.id.ActivityList);

        // get data from the table by the ListAdapter
        customAdapter = new CustomAdapter(getActivity(), R.layout.list_items, actionList);

        lv.setAdapter(customAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Sovellus", i + " painettu");
                saveData();
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
        loadData();
    }


   public void onPause(){
        super.onPause();
        Log.d("Sovellus", "OutputFragment onResume");
        saveData();
    }

    private void saveData(){
        pref = t.getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        listGson = new Gson();
        String json = listGson.toJson(actionList);
        prefEditor.putString("actionList", json);
        prefEditor.apply();
        Log.d("Sovellus", "actionList saved");
    }

    public void loadData(){
        pref = t.getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        listGson = new Gson();
        json = pref.getString("actionList", null);
        Type type = new TypeToken<ArrayList<Action>>(){}.getType();
        actionList = listGson.fromJson(json, type);
        Log.d("Sovellus", "actionList loaded");

        if(actionList == null){
            Log.d("Sovellus", "actionList null");
            actionList = new ArrayList<>();
        }
    }

    public void refreshAdapter() {
       customAdapter.notifyDataSetChanged();
    }
}

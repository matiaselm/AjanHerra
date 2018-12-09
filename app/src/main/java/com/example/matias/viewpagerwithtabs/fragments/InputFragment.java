package com.example.matias.viewpagerwithtabs.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matias.viewpagerwithtabs.R;
import com.example.matias.viewpagerwithtabs.classes.Action;
import com.example.matias.viewpagerwithtabs.singletons.ActionList;
import com.example.matias.viewpagerwithtabs.singletons.UserList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */

public class InputFragment extends Fragment {

    public InputFragment() {
        // Required empty public constructor
    }

    private int selectedAction;
    private View v;
    private TextView time;

    private Button start;

    Date date;

    private Long startTime;
    private Long endTime;
    SharedPreferences pref;
    Boolean isTimer;
    SharedPreferences.Editor prefEditor;

    int finalValue;
    int customTime;
    EditText cTime;
    TextView vTime;

    String activityName;
    String runningActivity;
    ArrayList actionList;

    String UsersActionList;

    String userIsTimer;
    String userStartInfo;
    String userRunningActivity;
    String userStartTime;
    String userRunningActivityInt;

    ArrayAdapter<String> dataAdapter;

    Gson listGson;
    String json;

    Time today;

    /**
     * Käyttäjä kirjautuu sisään 1. kerran: ("isTimer" = false)
     * -> napissa lukee "Aloita"
     * -> Tekstikentässä ohjeet "Valitse aktiviteetti ja paina aloita"
     * <p>
     * Käyttäjä painaa "ALOITA":
     * -> ("isTimer" = true)
     * -> Napissa lukee "Lopeta"
     * -> Tekstikentässä "Aktiviteetti aloitettu "KELLONAIKA"
     * <p>
     * Käyttäjä lähtee ruudusta ja tulee takaisin: ("isTimer" == true)
     * -> Nappulassa lukee "lopeta"
     * -> Tekstikentässä "Aktiviteetti aloitettu "KELLONAIKA"
     * <p>
     * Käyttäjä painaa lopeta:
     * -> ("isTimer" = false)
     * -> Nappulassa lukee "Aloita"
     * -> Tekstikentässä "Aktiviteetin kesto: "KESTO", lisää uusi aktiviteetti valitsemalla ja painamalla aloita
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Log.d("Sovellus", "onCreateView InputFragment");
        v = inflater.inflate(R.layout.fragment_input, container, false);

        pref = this.getActivity().getSharedPreferences("time", Activity.MODE_PRIVATE);
        prefEditor = pref.edit();
        userIsTimer = "user" + UserList.getInstance().getCurrentUser() + "isTimer";
        isTimer = pref.getBoolean(userIsTimer, false);

        time = v.findViewById(R.id.timeView);

        //actionList = ActionList.getInstance().getActivities();


        //Ei voi kutsia actionList ennenkuin se on luotu/määritelty!!
        //Tähän joku tapa määritellä actionList siten että se ei ole sama joka kerta

        loadData();

        start = v.findViewById(R.id.startButton);
        today = new Time(Time.getCurrentTimezone());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isTimer == false) {

                    startTime = System.currentTimeMillis() / 1000 / 60;
                    Log.d("Sovellus", "start time: " + String.valueOf(startTime));

                    today.setToNow();
                    runningActivity = activityName;

                    userRunningActivity = "user" + UserList.getInstance().getCurrentUser() + "RunningActivity";
                    userStartTime = "user" + UserList.getInstance().getCurrentUser() + "startTime";
                    userRunningActivityInt = "user" + UserList.getInstance().getCurrentUser() + "runningActivityInt";

                    prefEditor.putString(userRunningActivity, runningActivity);
                    userStartInfo = "user" + UserList.getInstance().getCurrentUser() + "startInfo";

                    if (today.minute < 10) {
                        String addTimeText = runningActivity + " aloitettu  \n" + today.hour + ":0" + today.minute;
                        time.setText (addTimeText);
                        prefEditor.putString(userStartInfo, addTimeText);
                    } else {
                        String addTimeText2 = runningActivity + " aloitettu  \n" + today.hour + ":" + today.minute;
                        time.setText(addTimeText2);
                        prefEditor.putString(userStartInfo, addTimeText2);
                    }

                    prefEditor.putLong(userStartTime, startTime);
                    prefEditor.putInt(userRunningActivityInt, selectedAction);

                    isTimer = true;
                    start.setText("Lopeta");
                    userIsTimer = "user" + UserList.getInstance().getCurrentUser() + "isTimer";
                    prefEditor.putBoolean(userIsTimer, true);
                    prefEditor.apply();

                    Log.d("Sovellus", "Aloitettu aktiviteetti: " + Integer.toString(selectedAction));

                } else if (isTimer == true) {
                    Log.d("Sovellus", "else if");

                    userRunningActivity = "user" + UserList.getInstance().getCurrentUser() + "RunningActivity";

                    userStartTime = "user" + UserList.getInstance().getCurrentUser() + "startTime";

                    Long startTimeMemory = pref.getLong(userStartTime, 0);
                    int savedActivity = pref.getInt(userRunningActivityInt, 0);
                    runningActivity = pref.getString(userRunningActivity, "");

                    endTime = System.currentTimeMillis() / 1000 / 60;
                    Log.d("Sovellus", "Endtime " + String.valueOf(endTime));

                    isTimer = false;
                    userIsTimer = "user" + UserList.getInstance().getCurrentUser() + "isTimer";
                    prefEditor.putBoolean(userIsTimer, false);
                    start.setText("Aloita");

                    Long dtime = endTime - startTimeMemory;

                    time.setText(runningActivity + " aktiviteettiin lisätty:\n" + Long.toString(dtime) + " min");
                    int sendTime = Math.toIntExact(dtime);

                    Log.d("Sovellus", "Current savedActivity: " + Integer.toString(savedActivity));
                    ActionList.getInstance().addAction(savedActivity, sendTime);

                    String activityAdded = "Lisätty\n" + sendTime + " minuuttia\n" + "aktiviteettiin\n" + ActionList.getInstance().getActivities().get(savedActivity).getType();
                    Toast.makeText(getContext(), activityAdded,
                            Toast.LENGTH_SHORT).show();

                    Log.d("Sovellus", activityAdded);

                    saveData();
                    prefEditor.apply();
                }
            }
        });

        //GetCurrentTimeInMillis;

        v.findViewById(R.id.addInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cTime = (EditText) v.findViewById(R.id.timeView2);
                String timeInput = cTime.getText().toString();
                if (timeInput.isEmpty()) {
                    Toast.makeText(getContext(), "Syötä aika",
                            Toast.LENGTH_SHORT).show();
                } else {
                    customTime = Integer.parseInt(timeInput);
                    if (customTime > 0) {
                        ActionList.getInstance().addAction(selectedAction, customTime);
                        String activityAdded = "Lisätty\n" + customTime + " minuuttia\n" + "aktiviteettiin\n" + ActionList.getInstance().getActivities().get(selectedAction).getType();
                        Log.d("Sovellus", activityAdded);

                        Toast.makeText(getContext(), activityAdded,
                                Toast.LENGTH_SHORT).show();
                        closeKeyboard();
                        saveData();
                    } else {
                        Toast.makeText(getContext(), "Virheellinen aika",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        v.findViewById(R.id.rmInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start.setText("Aloita");
                time.setText("Valitse uusi aktiviteetti ja aloita se painamalla \"aloita\"");

                vTime = v.findViewById(R.id.timeView2);
                vTime.setText("");

               /* ActionList.getInstance().addAction(selectedAction, Integer.parseInt(customTime));
                Log.d("Sovellus", "Input aktiviteetti tyhjennetty");*/

                Toast.makeText(getContext(), "Aktiviteetti tyhjennetty",
                        Toast.LENGTH_SHORT).show();

                closeKeyboard();

                isTimer = false;
                userIsTimer = "user" + UserList.getInstance().getCurrentUser() + "isTimer";
                prefEditor.putBoolean(userIsTimer, false);
                prefEditor.apply();
            }
        });

        return v;
    }

    @Override
    public void onResume() {

        super.onResume();

        Log.d("Sovellus", "Input onResume");

        Log.d("Sovellus", "Current user: " + UserList.getInstance().getCurrentUser());

        //Update user's actionList array
        loadData();


        TextView hello = v.findViewById(R.id.tvHello);
        hello.setText("Hei " + UserList.getInstance().getCurrentUser().getName() + "!");
        //time.setText(hours + ":" + minutes);

        userIsTimer = "user" + UserList.getInstance().getCurrentUser() + "isTimer";
        isTimer = pref.getBoolean(userIsTimer, false);

        Log.d("Sovellus", "isTimer = " + Boolean.toString(isTimer));

        if (isTimer == true) {

            start.setText("Lopeta");

            userStartInfo = "user" + UserList.getInstance().getCurrentUser() + "startInfo";
            time.setText(pref.getString(userStartInfo, "e"));

        } else if (isTimer == false) {

            start.setText("Aloita");
            time.setText("Valitse uusi aktiviteetti ja aloita se painamalla \"aloita\"");

        }

        dataAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, actionList);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinnerActions);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Object item = adapterView.getItemAtPosition(position);
                // if (item != null) {
                //    Toast.makeText(InputActivity.this, item.toString(),
                //            Toast.LENGTH_SHORT).show();
                //}
                selectedAction = position;
                Log.d("Sovellus", "Valittu aktiviteetti: " + Integer.toString(selectedAction));
                activityName = ActionList.getInstance().getActivities().get(selectedAction).getType();
                Log.d("Sovellus", activityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void saveData() {
        UsersActionList = "user" + UserList.getInstance().getCurrentUser() + "actionList";
        actionList = ActionList.getInstance().getActivities();
        listGson = new Gson();
        String json = listGson.toJson(actionList);
        prefEditor.putString(UsersActionList, json);
        prefEditor.apply();
        Log.d("Sovellus", "Data saved: " + json);
    }

    private void loadData() {
        UsersActionList = "user" + UserList.getInstance().getCurrentUser() + "actionList";
        listGson = new Gson();
        json = pref.getString(UsersActionList, null);
        Type type = new TypeToken<ArrayList<Action>>() {
        }.getType();
        actionList = listGson.fromJson(json, type);

        /**
         * If we cannot find any arraylist, use the default list. Else use the list found from memory.
         */
        if (actionList == null) {
            Log.d("Sovellus", "actionList null");

            //Change ActionList array into defaultList
            ActionList.getInstance().setDefaultList();
            actionList = ActionList.getInstance().getActivities();
            Log.d("Sovellus", "Data loaded: " + json);
            saveData();
        } else {
            ActionList.getInstance().setActivities(actionList);
            Log.d("Sovellus", "Data loaded: " + json);
        }
        /*if(actionList == null){
            Log.d("Sovellus", "actionList null");
            //Use default list
            actionList = ActionList.getInstance().getActivities();

        }*/
    }

    public void closeKeyboard() {

        try {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }

    }
}
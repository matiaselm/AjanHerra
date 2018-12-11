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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This fragment is the ultimate fragment to gather all user action inputs. Everything happening here is save in saved preferences.
 * Action spinner is used to select action by type.
 * Start and stop button is calculating time and adding action based on that calculation. Even when closing app this is saved. The action is saved also.
 * Manual time input is independent, because start and stop button reads and saves the action type at the time of pressing.
 * Current action selection is saved and restored after project closing to help user.
 * Reset button resets start and stop button and manual input feeds.
 * Manual input clears the text fields.
 */

public class InputFragment extends Fragment {

    public InputFragment() {
        // Required empty public constructor
    }

    private int selectedAction;
    private View v;
    private TextView time;

    private Button start;

    private Long startTime;
    private Long endTime;
    Boolean isTimer;

    SharedPreferences.Editor prefActionsEditor;
    SharedPreferences prefActions;

    int finalValue;
    int customTime;
    EditText timeHours;
    EditText timeMinutes;
    TextView hTime;
    TextView mTime;

    String activityName;
    String runningActivity;
    ArrayList actionList;
    List historyList;

    String usersActionList;

    private ArrayList userList;
    SharedPreferences prefUsers;
    SharedPreferences.Editor prefUsersEditor;

    String currentDate;
    String userIsTimer;
    String userStartInfo;
    String userRunningActivity;
    String userStartTime;
    String userRunningActivityInt;
    String usersLastActionSelection;

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

    /**
     * onCreateView we create listeners, inflate view and define couple parameters.
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
        Log.d("Sovellus", "onCreateView InputFragment");
        v = inflater.inflate(R.layout.fragment_input, container, false);

        time = v.findViewById(R.id.timeView);

        historyList = new ArrayList<String>();

        start = v.findViewById(R.id.startButton);
        today = new Time(Time.getCurrentTimezone());

        /**
         * This listener handles the start and stop.
         * It reads the current state from activity (which is loaded from shared preferences on resume)
         * State change is saved directly into shared prefs
         * isTimer == false -> start counter
         * isTimer == true -> stop counter
         * 1 minute is shortest time that is saved
         */
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isTimer == false) {

                    //Update the Strings used for Timer related Save strings
                    updateUserSaveKeys();

                    startTime = System.currentTimeMillis() / 1000 / 60;
                    Log.d("Sovellus", "start time: " + String.valueOf(startTime));

                    today.setToNow();
                    runningActivity = activityName;

                    prefActionsEditor.putString(userRunningActivity, runningActivity);

                    if (today.minute < 10) {
                        String addTimeText = runningActivity + " aloitettu  \n" + today.hour + ":0" + today.minute;
                        time.setText(addTimeText);
                        prefActionsEditor.putString(userStartInfo, addTimeText);
                    } else {
                        String addTimeText2 = runningActivity + " aloitettu  \n" + today.hour + ":" + today.minute;
                        time.setText(addTimeText2);
                        prefActionsEditor.putString(userStartInfo, addTimeText2);
                    }

                    prefActionsEditor.putLong(userStartTime, startTime);
                    prefActionsEditor.putInt(userRunningActivityInt, selectedAction);

                    isTimer = true;
                    start.setText("Lopeta");

                    prefActionsEditor.putBoolean(userIsTimer, true);
                    prefActionsEditor.apply();

                    Log.d("Sovellus", "Aloitettu aktiviteetti: " + Integer.toString(selectedAction));

                } else if (isTimer == true) {

                    //Update the Strings used for Timer related Save strings
                    updateUserSaveKeys();

                    Log.d("Sovellus", "else if");

                    Long startTimeMemory = prefActions.getLong(userStartTime, 0);
                    int savedActivity = prefActions.getInt(userRunningActivityInt, 0);
                    runningActivity = prefActions.getString(userRunningActivity, "");

                    endTime = System.currentTimeMillis() / 1000 / 60;
                    Log.d("Sovellus", "Endtime " + String.valueOf(endTime));


                    isTimer = false;

                    prefActionsEditor.putBoolean(userIsTimer, false);
                    prefActionsEditor.apply();
                    start.setText("Aloita");

                    Long dtime = endTime - startTimeMemory;

                    time.setText(runningActivity + " aktiviteettiin lisätty:\n" + Long.toString(dtime) + " min");
                    int sendTime = Math.toIntExact(dtime);

                    if (sendTime < 1) {
                        Toast.makeText(getContext(), "Ajanotto keskeytetty",
                                Toast.LENGTH_SHORT).show();

                    } else {

                        Log.d("Sovellus", "Current savedActivity: " + Integer.toString(savedActivity));
                        ActionList.getInstance().addAction(savedActivity, sendTime);

                        String activityAdded = "Lisätty\n" + sendTime + " minuuttia\n" + "aktiviteettiin\n" + ActionList.getInstance().getActivities().get(savedActivity).getType();
                        Toast.makeText(getContext(), activityAdded,
                                Toast.LENGTH_SHORT).show();

                        Log.d("Sovellus", activityAdded);


                        writeHistory(sendTime);
                    }
                }
            }
        });

        /**
         * This listener is add manual input button. Before proceeding add time we wheck:
         * One or both edit text fields have input
         * Count first field as hours * 60 = minutes
         * Second field is minutes
         * Sum total time and send to addTime
         * Edittext is set to number mode only
         */
        v.findViewById(R.id.addInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timeHours = (EditText) v.findViewById(R.id.timeView2);
                timeMinutes = (EditText) v.findViewById(R.id.timeView3);

                String hourInput = timeHours.getText().toString();
                String minuteInput = timeMinutes.getText().toString();

                if (hourInput.isEmpty() && minuteInput.isEmpty()) {
                    Toast.makeText(getContext(), "Syötä aika",
                            Toast.LENGTH_SHORT).show();
                } else if (hourInput.isEmpty()) {
                    customTime = Integer.parseInt(minuteInput);
                    addTime(customTime);
                } else if (minuteInput.isEmpty()) {
                    int hourInputMin = Integer.parseInt(hourInput) * 60;
                    customTime = hourInputMin;
                    addTime(customTime);
                } else {
                    int hourInputMin = Integer.parseInt(hourInput) * 60;
                    customTime = hourInputMin + Integer.parseInt(minuteInput);
                    addTime(customTime);
                }
            }
        });

        /**
         * Clears all inputs and timer.
         */
        v.findViewById(R.id.rmInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateUserSaveKeys();

                start.setText("Aloita");
                time.setText("Valitse uusi aktiviteetti ja aloita se painamalla \"aloita\"");

                hTime = v.findViewById(R.id.timeView2);
                hTime.setText("");
                mTime = v.findViewById(R.id.timeView3);
                mTime.setText("");

                Toast.makeText(getContext(), "Aktiviteetti tyhjennetty",
                        Toast.LENGTH_SHORT).show();

                closeKeyboard();

                isTimer = false;

                prefActionsEditor.putBoolean(userIsTimer, false);
                prefActionsEditor.apply();
            }
        });

        return v;
    }


    /**
     * Check from memory if the timer button is pressed. Set accordingly isTimer.
     * Get last selectedAction and set the spinner ready for user ease.
     * Read actionList from shared preferences and update the action spinner.
     */
    @Override
    public void onResume() {

        super.onResume();

        Log.d("Sovellus", "Input onResume");
        Log.d("Sovellus", "today: " + today.format("YYYY/MM/dd"));
        Log.d("Sovellus", "Current user: " + UserList.getInstance().getCurrentUser());

        //Update user's actionList array
        loadData();

        TextView hello = v.findViewById(R.id.tvHello);
        hello.setText("Hei " + UserList.getInstance().getCurrentUser().getName() + "!");
        //time.setText(hours + ":" + minutes);

        isTimer = prefActions.getBoolean(userIsTimer, false);

        Log.d("Sovellus", "isTimer = " + Boolean.toString(isTimer));

        if (isTimer == true) {

            start.setText("Lopeta");

            time.setText(prefActions.getString(userStartInfo, "e"));

        } else if (isTimer == false) {

            start.setText("Aloita");
            time.setText("Valitse uusi aktiviteetti ja aloita se painamalla \"aloita\"");

        }

        dataAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, actionList);
        Spinner spinnerActions = (Spinner) v.findViewById(R.id.spinnerActions);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerActions.setAdapter(dataAdapter);

        int LastActionSelection = prefActions.getInt(usersLastActionSelection, 0);
        spinnerActions.setSelection(LastActionSelection);
        selectedAction = LastActionSelection;

        /**
         * Select the action that is wanted to add. Save it into memory. selectedAction is used further on.
         */
        spinnerActions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                selectedAction = position;

                prefActionsEditor.putInt(usersLastActionSelection, position);
                prefActionsEditor.apply();
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

    /**
     * Get all sharedpreference keys updated in one go and never ever mess them again. Aye.
     */
    private void updateUserSaveKeys() {
        int getCurrentUserInt = UserList.getInstance().getCurrentUserInt();
        userRunningActivity = "user" + getCurrentUserInt + "RunningActivity";
        userStartTime = "user" + getCurrentUserInt + "StartTime";
        userRunningActivityInt = "user" + getCurrentUserInt + "RunningActivityInt";
        userStartInfo = "user" + getCurrentUserInt + "StartInfo";
        userIsTimer = "user" + getCurrentUserInt + "IsTimer";
        usersActionList = "user" + getCurrentUserInt + "ActionList";
        usersLastActionSelection = "user" + getCurrentUserInt + "ActionListSelection";
    }

    /**
     * Add time and check that we cannot set more than 24 at one time.
     *
     * @param customTime
     */
    private void addTime(int customTime) {
        if (customTime > 0 && customTime <= 1440) {
            ActionList.getInstance().addAction(selectedAction, customTime);

            String activityAdded = "Lisätty\n" + customTime + " minuuttia\n" + "aktiviteettiin\n" + ActionList.getInstance().getActivities().get(selectedAction).getType();
            Log.d("Sovellus", activityAdded);

            Toast.makeText(getContext(), activityAdded,
                    Toast.LENGTH_SHORT).show();
            writeHistory(customTime);
            closeKeyboard();

            hTime = v.findViewById(R.id.timeView2);
            hTime.setText("");
            mTime = v.findViewById(R.id.timeView3);
            mTime.setText("");

        } else {
            Toast.makeText(getContext(), "Virheellinen aika",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodi muuntaa kutsumahetkellä actionListin gsoniin,
     * tallentaa sen yhtenä Stringinä sharedPrefereseihin
     * avaimena käytetään jokaiselle käyttäjälle henkilökohtaista
     * muuttujaa usersActionList, jolla se myöhemmin haetaan loadData -metodissa
     */

    private void saveData() {
        updateUserSaveKeys();
        actionList = ActionList.getInstance().getActivities();
        listGson = new Gson();
        String json = listGson.toJson(actionList);
        prefActionsEditor.putString(usersActionList, json);
        prefActionsEditor.apply();
        Log.d("Sovellus", "Data saved: " + usersActionList + " : " + json);
    }

    /**
     * Try loading the users actionList. If we don't find it, then we use the default list.
     */
    private void loadData() {
        prefActions = this.getActivity().getSharedPreferences("Actions", Activity.MODE_PRIVATE);
        prefActionsEditor = prefActions.edit();

        updateUserSaveKeys();
        listGson = new Gson();
        json = prefActions.getString(usersActionList, null);
        Type type = new TypeToken<ArrayList<Action>>() {
        }.getType();
        actionList = listGson.fromJson(json, type);

        /**
         * If we cannot find any arraylist, use the default list. Else use the list found from memory.
         */
        if (actionList == null) {
            Log.d("Sovellus", "actionList null , using default actionlist");

            //Change ActionList array into defaultList
            ActionList.getInstance().setDefaultList();
            saveData();
        } else {
            ActionList.getInstance().setActivities(actionList);
            Log.d("Sovellus", "Data loaded: " + usersActionList + " : " + json);
        }
    }

    /**
     * Write entry to history event log.
     * Format: entryNumber Date / action / added time
     * Example : 43)  11.12.2018 21.14/Työskentely, lisätty 1 min
     * This data is saved under current user and sorted in reverse order
     * FrontAdapter divides and prints this as before and after character "/"
     * <p>
     * 44)  11.12.2018 21.15
     * Nukkuminen, lisätty 50 min
     * <p>
     * 43)  11.12.2018 21.14
     * Työskentely, lisätty 1 min
     *
     * @param contextTime Minutes added
     */
    public void writeHistory(int contextTime) {
        if (contextTime > 0) {

            currentDate = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(new Date());

            activityName = ActionList.getInstance().getActivities().get(selectedAction).getType();
            long entryNumber = UserList.getInstance().getCurrentUser().getHistoryList().size() + 1;
            String summary = entryNumber + ")  " + currentDate + "/" + activityName + ", lisätty " + contextTime + " min";

            Log.d("Sovellus", "History time: " + summary);

            UserList.getInstance().getCurrentUser().addHistoryEvent(summary);
        }

        prefUsers = getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
        prefUsersEditor = prefUsers.edit();

        userList = UserList.getInstance().getUsers();
        listGson = new Gson();
        json = listGson.toJson(userList);
        Log.d("Sovellus", "Users saved: " + json);
        prefUsersEditor.putString("userList", json);
        prefUsersEditor.commit();

        saveData();
    }

    /**
     * /**
     * Manually close the keyboard
     */
    public void closeKeyboard() {

        try {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }
}
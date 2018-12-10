package com.example.matias.viewpagerwithtabs.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matias.viewpagerwithtabs.R;
import com.example.matias.viewpagerwithtabs.classes.Action;
import com.example.matias.viewpagerwithtabs.singletons.UserList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private SettingsActivity thisActivity;
    private int currentYear;

    private Spinner spinnerAge;
    private Spinner spinnerSex;
    private String selectedName;
    private int selectedYear;
    private int selectedSex;

    EditText editName;
    private ArrayList userList;
    String usersActionList;
    SharedPreferences prefUsers;
    SharedPreferences.Editor prefUsersEditor;
    SharedPreferences prefActions;
    SharedPreferences.Editor prefActionsEditor;
    Gson listGson;
    String json;
    String empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //********************TOOLBAR STUFF ONLY***********************//
        Toolbar toolbar = findViewById(R.id.returnBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //********************TOOLBAR STUFF ONLY***********************//

        empty = null;

        this.thisActivity = this;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        currentYear = today.year;

        editName = (EditText) findViewById(R.id.etNameSettings);

        List<String> ageList = new ArrayList<String>();
        List<String> sexList = new ArrayList<String>();

        for (int i = currentYear; i > currentYear - 120; i--) {
            ageList.add("Syntymävuosi: " + i);
        }

        sexList.add("Sukupuoli: Mies");
        sexList.add("Sukupuoli: Nainen");
        sexList.add("Sukupuoli: Muu");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ageList);

        ArrayAdapter<String> dataAdapterSex = new ArrayAdapter<String>(this.thisActivity, android.R.layout.simple_spinner_item, sexList);

        spinnerAge = (Spinner) findViewById(R.id.spinnerAgeSettings);
        spinnerSex = (Spinner) findViewById(R.id.spinnerSexSettings);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinnerAge
        spinnerAge.setAdapter(dataAdapter);
        spinnerSex.setAdapter(dataAdapterSex);

        getUser();

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                selectedYear = currentYear - position;
                Log.d("Sovellus", "SelectedYear " + Integer.toString(selectedYear));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });


        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //0=Male 1=Female 2=Other
                selectedSex = position;

                Log.d("Sovellus", "Selected " + selectedSex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });

        findViewById(R.id.saveSettingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedName = editName.getText().toString();

                if (selectedName.isEmpty()) {
                    Toast.makeText(thisActivity, "Käyttäjänimi puuttuu",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (UserList.getInstance().testSettingsUserName(selectedName)) {
                        changeSettings();
                    } else {
                        Toast.makeText(thisActivity, "Käyttäjänimi varattu",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.removeUserButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Removes current user
                removeUser();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUser();
        loadUsers();
    }

    public void removeUser() {

        UserList.getInstance().removeUser();

        prefActions = getSharedPreferences("Actions", Activity.MODE_PRIVATE);
        prefActionsEditor = prefActions.edit();

        //Keep these two togethor
        int tryUserIndex = UserList.getInstance().getCurrentUserInt();
        int userListSize = UserList.getInstance().getUsers().size();


        int userIndexBeforeRemove = UserList.getInstance().getCurrentUserInt();
        usersActionList = "user" + userIndexBeforeRemove + "ActionList";


        json = prefActions.getString(usersActionList, null);

        Log.d("Sovellus", "Removed " + usersActionList + " : " + json);
        prefActionsEditor.putString(usersActionList, empty);
        prefActionsEditor.commit();

        json = prefActions.getString(usersActionList, null);
        Log.d("Sovellus", "After remove " + usersActionList + " : " + json);

        if (userIndexBeforeRemove == userListSize) {

            Log.d("Sovellus", "Deleted user was last in index");

        } else {

            for (int i = userIndexBeforeRemove; i < userListSize; i++) {

                //Keep these two togethor
                tryUserIndex += 1;
                usersActionList = "user" + tryUserIndex + "ActionList";

                Log.d("Sovellus", "Searching index " + tryUserIndex);


                json = prefActions.getString(usersActionList, null);
                Type type = new TypeToken<ArrayList<Action>>() {
                }.getType();

                listGson = new Gson();
                ArrayList actionList = listGson.fromJson(json, type);

                if (actionList == null) {
                    Log.d("Sovellus", "actionList null");
                    break;
                } else {
                    //Keep these two togethor
                    tryUserIndex -= 1;
                    usersActionList = "user" + tryUserIndex + "ActionList";

                    String jsonOut = listGson.toJson(actionList);
                    Log.d("Sovellus", "Added " + usersActionList + jsonOut);
                    prefActionsEditor.putString(usersActionList, jsonOut);
                    prefActionsEditor.commit();

                    //Keep these two togethor
                    tryUserIndex += 1;
                    usersActionList = "user" + tryUserIndex + "ActionList";

                    Log.d("Sovellus", "Removed " + usersActionList);
                    prefActionsEditor.putString(usersActionList, empty);
                    prefActionsEditor.commit();
                }
            }
        }

        saveUsers();

        prefUsersEditor.putInt("currentUser", -1);
        prefUsersEditor.commit();

        Intent nextActivity = new Intent(thisActivity, MainActivity.class);
        startActivity(nextActivity);
    }


    public void getUser() {
        int putAge = currentYear - UserList.getInstance().getCurrentUser().getYearOfBirth();

        selectedSex = UserList.getInstance().getCurrentUser().getSexInt();
        selectedName = UserList.getInstance().getCurrentUser().getName();
        selectedYear = UserList.getInstance().getCurrentUser().getYearOfBirth();


        spinnerAge.setSelection(putAge);
        spinnerSex.setSelection(selectedSex);
        editName.setText(selectedName, TextView.BufferType.EDITABLE);

        Log.d("Sovellus", "Loaded user settings " + UserList.getInstance().getCurrentUser().getName() + " Name " + selectedName + " Year of birth " + selectedYear + " Gender " + selectedSex + UserList.getInstance().getCurrentUser().getSex() + UserList.getInstance().getCurrentUser().getSexInt());
    }

    public void saveUsers() {
        userList = UserList.getInstance().getUsers();
        listGson = new Gson();
        json = listGson.toJson(userList);
        Log.d("Sovellus", "Users saved: " + json);
        prefUsersEditor.putString("userList", json);
        prefUsersEditor.commit();
    }

    public void loadUsers() {
        prefUsers = getSharedPreferences("Users", Activity.MODE_PRIVATE);
        prefUsersEditor = prefUsers.edit();
    }

    public void changeSettings() {
        Log.d("Sovellus", "Changed user settings " + UserList.getInstance().getCurrentUser().getName() + " Name " + selectedName + " Year of birth " + selectedYear + " Gender " + selectedSex);
        UserList.getInstance().getCurrentUser().setSex(selectedSex);
        UserList.getInstance().getCurrentUser().setName(selectedName);
        UserList.getInstance().getCurrentUser().setYearOfBirth(selectedYear);

        saveUsers();

        //    Toast.makeText(thisActivity, "Tallennus onnistui!",
        //            Toast.LENGTH_SHORT).show();
    }

    //********************TOOLBAR STUFF ONLY***********************//

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Asetukset on ladattu",
                        Toast.LENGTH_SHORT).show();

                // Intent settingsActivity = new Intent(this, SettingsActivity.class);
                // startActivity(settingsActivity);
                break;

            case R.id.action_add_action:

                Intent addActivity = new Intent(this, AddActivity.class);
                startActivity(addActivity);
                break;

            case R.id.action_info:
                Toast.makeText(this, "AjanHerra\n Versio: 1.0.0\n\nTekijät:\n\nHenri Lagerroos\nMatias Jalava",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.action_logout:

                prefUsersEditor.putInt("currentUser", -1);
                prefUsersEditor.commit();

                Log.d("Sovellus", "Logged out user" + UserList.getInstance().getCurrentUserInt());

                Intent logoutActivity = new Intent(this, MainActivity.class);
                startActivity(logoutActivity);
                break;

            default:
                //error
        }
        return super.onOptionsItemSelected(item);
    }
    //********************TOOLBAR STUFF ONLY***********************//
}

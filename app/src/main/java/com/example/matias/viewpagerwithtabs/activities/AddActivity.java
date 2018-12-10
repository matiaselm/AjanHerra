package com.example.matias.viewpagerwithtabs.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matias.viewpagerwithtabs.R;
import com.example.matias.viewpagerwithtabs.classes.Action;
import com.example.matias.viewpagerwithtabs.singletons.ActionList;
import com.example.matias.viewpagerwithtabs.singletons.UserList;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    SharedPreferences.Editor prefActionsEditor;
    SharedPreferences prefActions;
    ArrayList actionList;
    Gson listGson;
    String json;

    String usersActionList;

    private String type;
    private boolean needMoreThan;
    private double referenceHours;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //********************TOOLBAR STUFF ONLY***********************//
        Toolbar toolbar = findViewById(R.id.returnBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //********************TOOLBAR STUFF ONLY***********************//

        type = "s";
        needMoreThan = true;
        referenceHours = 3;
        description = "";

        loadPref();
        inputAddButtonPressed();

    }

    public void loadPref() {
        prefActions = getSharedPreferences("Actions", Activity.MODE_PRIVATE);
        prefActionsEditor = prefActions.edit();
    }

    public void savePref() {
        actionList = ActionList.getInstance().getActivities();
        listGson = new Gson();
        String json = listGson.toJson(actionList);
        prefActionsEditor.putString(usersActionList, json);
        prefActionsEditor.apply();
        Log.d("Sovellus", "Added new Activity. Data saved: " + usersActionList + " : " + json);

    }

    /**
     * First check if Type name is used. Then check that all inputs are in place before adding new action.
     */
    public void inputAddButtonPressed() {
            if (type.isEmpty() || referenceHours > 24 || description.isEmpty()) {
                Toast.makeText(this, "Virheellinen syöte",
                        Toast.LENGTH_SHORT).show();
                Log.d("Sovellus", "Tried adding false new activity inputs");
            } else {
                if (ActionList.getInstance().testNewActivityName(type)) {
                addNewAction();
            }else {
                    Toast.makeText(this, "Aktiviteetin nimi käytössä",
                            Toast.LENGTH_SHORT).show();
                }

        }
    }

    public void addNewAction() {
        usersActionList = "user" + UserList.getInstance().getCurrentUserInt() + "ActionList";

        if (referenceHours <= 0){
            referenceHours = -1;
        }

        ActionList.getInstance().addNewActionType(type, needMoreThan, referenceHours, description);

        Toast.makeText(this, "Aktiviteetti lisätty",
                Toast.LENGTH_SHORT).show();

        savePref();
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
                //Toast.makeText(this, "Asetukset",
                //        Toast.LENGTH_SHORT).show();

                Intent settingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(settingsActivity);
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

                SharedPreferences prefUsers = getSharedPreferences("Users", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefUsersEditor = prefUsers.edit();
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

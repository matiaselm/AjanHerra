package com.example.matias.viewpagerwithtabs.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.matias.viewpagerwithtabs.R;
import com.example.matias.viewpagerwithtabs.singletons.ActionList;
import com.example.matias.viewpagerwithtabs.singletons.UserList;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    SharedPreferences.Editor prefActionsEditor;
    SharedPreferences prefActions;
    ArrayList actionList;
    Gson listGson;
    String json;
    private AddActivity thisActivity;
    String usersActionList;


    private boolean selectedNeedMoreThan;
    private double selectedReferenceHours;
    private String selectedDescription;
    private String selectedType;
    private String timeInput;

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

        this.thisActivity = this;
        loadPref();

        selectedNeedMoreThan = true;

        RadioGroup booleanGroup = findViewById(R.id.booleanMoreGroup);

        booleanGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                switch (rb.getId()) {
                    case R.id.booleanMoreButton: {
                        Log.d("lab03", "ButtonMore valittu");
                        selectedNeedMoreThan = true;
                        break;
                    }
                    case R.id.booleanLessButton: {
                        Log.d("lab03", "ButtonLess valittu");
                        selectedNeedMoreThan = false;
                        break;
                    }
                }
            }
        });

        findViewById(R.id.addActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputAddButtonPressed();
            }
        });

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
        Log.d("Sovellus", "Added new Activity." + usersActionList + selectedType + selectedNeedMoreThan + selectedReferenceHours + selectedDescription);

        Log.d("Sovellus", "Added Activity, Data saved: " + " : " + json);

    }

    /**
     * First check if Type name is used. Then check that all inputs are in place before adding new action.
     */
    public void inputAddButtonPressed() {

        EditText editType = (EditText) findViewById(R.id.etActionType);
        selectedType = editType.getText().toString();

        EditText editTime = (EditText) findViewById(R.id.etActionTime);
        timeInput = editTime.getText().toString();

        EditText editDescription = (TextInputEditText) findViewById(R.id.etActionDescription);
        selectedDescription = editDescription.getText().toString();

        if (selectedType.isEmpty()) {
            Toast.makeText(this, "Aktiviteetin nimi puuttuu",
                    Toast.LENGTH_SHORT).show();
            Log.d("Sovellus", "Aktiviteetin nimi puuttuu");
        } else if (timeInput.isEmpty()) {
            Toast.makeText(this, "Tuntimäärä puuttuu",
                    Toast.LENGTH_SHORT).show();
            Log.d("Sovellus", "Tuntimäärä puuttuu");
        } else if (Double.parseDouble(timeInput) > 24) {
            Toast.makeText(this, "Tuntimäärä liian suuri",
                    Toast.LENGTH_SHORT).show();
            Log.d("Sovellus", "Tuntimäärä liian suuri");
        } else if (selectedDescription.isEmpty()) {
            Toast.makeText(this, "Aktiviteetin kuvaus puuttuu",
                    Toast.LENGTH_SHORT).show();
            Log.d("Sovellus", "Aktiviteetin kuvaus puuttuu");
        } else {
            if (ActionList.getInstance().testNewActivityName(selectedType)) {
                selectedReferenceHours = Double.parseDouble(timeInput);
                Log.d("Sovellus", "Saving activity" + selectedType + selectedReferenceHours + selectedNeedMoreThan + selectedDescription);
                addNewAction();
            } else {
                Toast.makeText(this, "Aktiviteetin nimi käytössä",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void addNewAction() {
        usersActionList = "user" + UserList.getInstance().getCurrentUserInt() + "ActionList";

        if (selectedReferenceHours <= 0) {
            selectedReferenceHours = -1;
        }

        ActionList.getInstance().addNewActionType(selectedType, selectedNeedMoreThan, selectedReferenceHours, selectedDescription);

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

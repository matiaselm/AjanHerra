package com.example.matias.viewpagerwithtabs.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.matias.viewpagerwithtabs.R;
import com.example.matias.viewpagerwithtabs.classes.User;
import com.example.matias.viewpagerwithtabs.singletons.UserList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    private MainActivity thisActivity;
    private int selectedUserInt;
    private int selectedSex;
    private int selectedYearInt;

    private int currentYear;
    private String selectedName;
    private int selectedYear;

    private String currentUser;
    private Spinner spinnerAge;
    private Spinner spinnerSex;
    private boolean isAutomaticLogin;
    private ArrayList userList;

    SharedPreferences prefUsers;
    SharedPreferences.Editor prefEditor;
    Gson listGson;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedSex = 0;
        selectedYearInt = 0;
        isAutomaticLogin = true;



        Log.d("Sovellus", "On create");
        this.thisActivity = this;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        currentYear = today.year;

        List<String> ageList = new ArrayList<String>();
        List<String> sexList = new ArrayList<String>();

        for (int i = currentYear; i > currentYear - 120; i--) {
            ageList.add("Syntymävuosi: " + i);
        }

        //Do not change order!
        sexList.add("Sukupuoli: Mies");
        sexList.add("Sukupuoli: Nainen");
        sexList.add("Sukupuoli: Muu");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ageList);

        ArrayAdapter<String> dataAdapterSex = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexList);

        spinnerAge = (Spinner) findViewById(R.id.spinnerAge);
        spinnerSex = (Spinner) findViewById(R.id.spinnerSex);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinnerAge
        spinnerAge.setAdapter(dataAdapter);
        spinnerSex.setAdapter(dataAdapterSex);


        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                selectedYearInt = position;
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

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserList.getInstance().getUsers().size() == 0) {
                    Toast.makeText(MainActivity.this, "Ei käyttäjiä",
                            Toast.LENGTH_SHORT).show();
                    selectedUserInt = -1;
                } else {
                    changeActivity();
                }

            }
        });

        findViewById(R.id.newUserButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edit = (EditText) findViewById(R.id.etName);
                selectedName = edit.getText().toString();

                if (selectedName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Käyttäjänimi puuttuu",
                            Toast.LENGTH_SHORT).show();
                } else {

                    if (UserList.getInstance().getUsers().size() == 0) {
                        newUser();
                    } else {
                        if (UserList.getInstance().testNewUserName(selectedName)) {
                            newUser();
                        } else {
                            Toast.makeText(MainActivity.this, "Käyttäjänimi varattu",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        loadUsers();

        //userList = UserList.getInstance().getUsers();
        Log.d("Sovellus", "onResume");

        spinnerAge.setSelection(selectedYearInt);
        spinnerSex.setSelection(selectedSex);

        if (UserList.getInstance().getUsers().size() == 0) {
            selectedUserInt = -1;
            Log.d("Sovellus", "No users yet");
        } else {
            ArrayAdapter<String> dataAdapterUser = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userList);
            Spinner spinnerUser = (Spinner) findViewById(R.id.spinnerUsers);

            dataAdapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUser.setAdapter(dataAdapterUser);
            selectedUserInt = (prefUsers.getInt(currentUser, -1));
            spinnerUser.setSelection(selectedUserInt);
            spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {
                    selectedUserInt = position;
                    Log.d("Sovellus", "Selected user " + selectedUserInt);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }

    private void saveUsers() {
        userList = UserList.getInstance().getUsers();
        listGson = new Gson();
        json = listGson.toJson(userList);
        Log.d("Sovellus", "Users saved: " + json);
        prefEditor.putString("userList", json);
        prefEditor.commit();

    }

    public void loadUsers() {
        prefUsers = thisActivity.getSharedPreferences("Users", Activity.MODE_PRIVATE);
        prefEditor = prefUsers.edit();

        listGson = new Gson();
        json = prefUsers.getString("userList", null);
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        userList = listGson.fromJson(json, type);

        if (userList == null) {
            Log.d("Sovellus", "userList null");
            //Use default list
            userList = UserList.getInstance().getUsers();

        } else {
            UserList.getInstance().setUsers(userList);
            Log.d("Sovellus", "Users loaded: " + json);
        }

        selectedUserInt = (prefUsers.getInt(currentUser, -1));

        tryAutomaticLogin();
    }

    /**
     * Login automatically if we have a current user set >=0 in SharedPreferences and automatic login is enabled.
     */
    private void tryAutomaticLogin() {
        //if (isAutomaticLogin == true && selectedUserInt >= 0) {
        //    changeActivity();
        //}
    }

    private void newUser() {
        selectedUserInt = UserList.getInstance().getUsers().size();

        UserList.getInstance().addUser(selectedName, selectedYear, selectedSex);

        UserList.getInstance().setCurrentUser(selectedUserInt);

        Log.d("Sovellus", "Added user as Index " + selectedUserInt + " Name " + UserList.getInstance().getCurrentUser().getName() + " Age " + Integer.toString(UserList.getInstance().getCurrentUser().getAge()) + " Gender " + UserList.getInstance().getCurrentUser().getSex());

        changeActivity();
    }

    private void changeActivity() {

        saveUsers();

        //Toast.makeText(MainActivity.this, "Tervetuloa " + UserList.getInstance().getCurrentUser().getName(),
        //        Toast.LENGTH_SHORT).show();
        currentUser = "currentUser";

        prefEditor = thisActivity.prefUsers.edit();
        prefEditor.putInt(currentUser, selectedUserInt);
        prefEditor.commit();
        UserList.getInstance().setCurrentUser(selectedUserInt);
        Log.d("Sovellus", "Logged in as Index " + selectedUserInt + " Name " + UserList.getInstance().getCurrentUser().getName() + " Age " + Integer.toString(UserList.getInstance().getCurrentUser().getAge()) + " Gender " + UserList.getInstance().getCurrentUser().getSex()+ UserList.getInstance().getCurrentUser().getSexInt());

        Intent nextActivity = new Intent(thisActivity, MainActivityTabs.class);
        //Intent nextActivity = new Intent(thisActivity, Test.class);
        startActivity(nextActivity);
    }


}

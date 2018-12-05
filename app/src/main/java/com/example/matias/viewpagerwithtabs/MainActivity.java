package com.example.matias.viewpagerwithtabs;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MainActivity thisActivity;
    private boolean debug;

    private int selectedUserInt;

    private int currentYear;
    private String selectedName;
    private int selectedYear;
    private String selectedSex;
    SharedPreferences prefUsers;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("Sovellus", "On create");
        this.thisActivity = this;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        currentYear = today.year;

        loadUsers();

        debug = false;

        if (debug == true) {
            changeActivity();

        } else {
            Log.d("Yolo", "Once");
        }

        List<String> ageList = new ArrayList<String>();
        List<String> sexList = new ArrayList<String>();

        for (int i = currentYear; i > currentYear - 120; i--) {
            ageList.add("Syntymävuosi: " + i);
        }

        sexList.add("Sukupuoli: Mies");
        sexList.add("Sukupuoli: Nainen");
        sexList.add("Sukupuoli: Muu");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ageList);

        ArrayAdapter<String> dataAdapterSex = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexList);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerAge);
        Spinner spinnerSex = (Spinner) findViewById(R.id.spinnerSex);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinnerSex.setAdapter(dataAdapterSex);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                if (position == 0) {
                    selectedSex = "m";
                } else if (position == 1) {
                    selectedSex = "f";
                } else {
                    selectedSex = "o";
                }
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

        final ArrayList userList = UserList.getInstance().getUsers();

        if (UserList.getInstance().getUsers().size() == 0) {
            Log.d("App", "No users yet");
        } else {
            ArrayAdapter<String> dataAdapterUser = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userList);
            Spinner spinnerUser = (Spinner) findViewById(R.id.spinnerUsers);
            dataAdapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUser.setAdapter(dataAdapterUser);
            spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {
                    selectedUserInt = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }

    /**
     * Maximum of 100 unique users per device. Break when first empty user*index* appears.
     */
    public void loadUsers() {
        prefUsers = thisActivity.getSharedPreferences("Users", Activity.MODE_PRIVATE);

        boolean listScanned = false;

        for (int i = 0; i < 100; i++) {
            String user = "user" + i;
            String userSex = "user" + i + "Sex";
            String userYear = "user" + i + "Year";


            if (prefUsers.contains(user) && listScanned == false) {
                String userNameMem = prefUsers.getString(user, "username");
                String userSexMem = prefUsers.getString(userSex, "usersex");
                int userYearMem = prefUsers.getInt(userYear, 0);
                UserList.getInstance().addUser(userNameMem, userYearMem, userSexMem);
            } //else {
                //listScanned = true;
            //}
        }
    }

    private void newUser() {
        selectedUserInt = UserList.getInstance().getUsers().size();

        UserList.getInstance().addUser(selectedName, selectedYear, selectedSex);

        UserList.getInstance().setCurrentUser(selectedUserInt);

        prefEditor = thisActivity.prefUsers.edit();

        String user = "user" + selectedUserInt;
        String userName = "user" + UserList.getInstance().getUsers().size();
        String userYear = "user" + UserList.getInstance().getUsers().size() + "Year";
        String userSex = "user" + UserList.getInstance().getUsers().size() + "Sex";

        prefEditor.putString(userName, selectedName);
        prefEditor.putInt(userYear, selectedYear);
        prefEditor.putString(userSex, selectedSex);

        prefEditor.commit();

        Log.d("Sovellus", "Added user as Index " + selectedUserInt + " Name " + UserList.getInstance().getCurrentUser().getName() + " Age " + Integer.toString(UserList.getInstance().getCurrentUser().getAge()) + " Gender " + UserList.getInstance().getCurrentUser().getSex());

        changeActivity();
    }

    private void changeActivity() {
        Toast.makeText(MainActivity.this, "Tervetuloa " + UserList.getInstance().getCurrentUser().getName(),
                Toast.LENGTH_SHORT).show();
        UserList.getInstance().setCurrentUser(selectedUserInt);
        Log.d("Sovellus", "Logged in as Index " + selectedUserInt + " Name " + UserList.getInstance().getCurrentUser().getName() + " Age " + Integer.toString(UserList.getInstance().getCurrentUser().getAge()) + " Gender " + UserList.getInstance().getCurrentUser().getSex());

        Intent nextActivity = new Intent(thisActivity, MainActivityTabs.class);
        startActivity(nextActivity);
    }
}

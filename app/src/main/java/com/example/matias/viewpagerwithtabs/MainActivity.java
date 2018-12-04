package com.example.matias.viewpagerwithtabs;


import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivity thisActivity;
    private boolean debug;

    private int selectedUserInt;

    private int currentYear;
    private String selectedName;
    private int selectedYear;
    private String selectedSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.thisActivity = this;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        currentYear = today.year;

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

    private void newUser() {
        selectedUserInt = UserList.getInstance().getUsers().size();
        Log.d("Sovellus", "Added user " + selectedName + " " + selectedYear + " " + selectedSex);
        UserList.getInstance().addUser(selectedName, selectedYear, selectedSex);
        changeActivity();
    }

    private void changeActivity() {
        Toast.makeText(MainActivity.this, "Tervetuloa " + selectedName,
                Toast.LENGTH_SHORT).show();
        UserList.getInstance().setCurrentUser(selectedUserInt);
        Log.d("Sovellus", "Logged in as " + UserList.getInstance().getCurrentUser().getName());

        Intent nextActivity = new Intent(thisActivity, MainActivityTabs.class);
        startActivity(nextActivity);
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
}

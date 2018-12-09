package com.example.matias.viewpagerwithtabs.activities;

import android.content.Intent;
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
import com.example.matias.viewpagerwithtabs.singletons.UserList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //********************TOOLBAR STUFF ONLY***********************//
        Toolbar toolbar = findViewById(R.id.returnBar);
        setSupportActionBar(toolbar);
        //********************TOOLBAR STUFF ONLY***********************//

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUser();
    }

    public void getUser() {
        int putAge = currentYear - UserList.getInstance().getCurrentUser().getYearOfBirth();

        selectedSex = UserList.getInstance().getCurrentUser().getSexInt();
        selectedName = UserList.getInstance().getCurrentUser().getName();
        selectedYear = UserList.getInstance().getCurrentUser().getYearOfBirth();


        spinnerAge.setSelection(putAge);
        spinnerSex.setSelection(selectedSex);
        editName.setText(selectedName, TextView.BufferType.EDITABLE);

        Toast.makeText(thisActivity, "Loaded user settings " + UserList.getInstance().getCurrentUser().getName() + " Name " + selectedName + " Year of birth " + selectedYear + " Gender " + selectedSex + UserList.getInstance().getCurrentUser().getSex() + UserList.getInstance().getCurrentUser().getSexInt(),
                Toast.LENGTH_LONG).show();
    }

    public void changeSettings() {
        Log.d("Sovellus", "Changed user settings " + UserList.getInstance().getCurrentUser().getName() + " Name " + selectedName + " Year of birth " + selectedYear + " Gender " + selectedSex);
        UserList.getInstance().getCurrentUser().setSex(selectedSex);
        UserList.getInstance().getCurrentUser().setName(selectedName);
        UserList.getInstance().getCurrentUser().setYearOfBirth(selectedYear);

        Toast.makeText(thisActivity, "Saved user settings " + UserList.getInstance().getCurrentUser().getName() + " Name " + selectedName + " Year of birth " + selectedYear + " Gender " + selectedSex,
                Toast.LENGTH_LONG).show();

        //    Toast.makeText(thisActivity, "Tallennus onnistui!",
        //            Toast.LENGTH_SHORT).show();
    }

    //********************TOOLBAR STUFF ONLY***********************//
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

            case R.id.action_info:
                Toast.makeText(this, "AjanHerra\n Versio: 1.0.0\n\nTekijät:\n\nHenri Lagerroos\nMatias Jalava",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.action_logout:
                //Toast.makeText(this, "Kirjauduit ulos",
                //        Toast.LENGTH_SHORT).show();
                // SharedPreferences.Editor prefEditor;
                // prefEditor = MainActivity.prefUsers.edit();
                // prefEditor.putInt("currentUser", -1);
                // prefEditor.commit();
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

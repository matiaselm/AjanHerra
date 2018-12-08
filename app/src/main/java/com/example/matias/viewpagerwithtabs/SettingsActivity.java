package com.example.matias.viewpagerwithtabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private SettingsActivity thisActivity;
    private int currentYear;

    private Spinner spinnerAge;
    private Spinner spinnerSex;
    private String selectedName;
    private int selectedYear;
    private String selectedSex;
    private int selectedSexInt;
    private int selectedYearInt;
    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

                selectedSexInt = position;
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

        findViewById(R.id.saveSettingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedName = editName.getText().toString();

                if (selectedName.isEmpty()) {
                    Toast.makeText(thisActivity, "Käyttäjänimi puuttuu",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (UserList.getInstance().testNewUserName(selectedName) || selectedName == UserList.getInstance().getCurrentUser().getName()) {
                        changeSettings();
                    } else {
                        Toast.makeText(thisActivity, "Käyttäjänimi varattu",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void getUser() {
        int putAge = UserList.getInstance().getCurrentUser().getYearOfBirth() - currentYear;
        int putSex;
        selectedSex = UserList.getInstance().getCurrentUser().getSex();
        selectedName = UserList.getInstance().getCurrentUser().getName();
        selectedYear = UserList.getInstance().getCurrentUser().getYearOfBirth();

        if (selectedSex == "m") {
            putSex = 0;
        } else if (selectedSex == "w") {
            putSex = 1;
        } else {
            putSex = 2;
        }

        spinnerAge.setSelection(putAge);
        spinnerSex.setSelection(putSex);
        editName.setText(selectedName, TextView.BufferType.EDITABLE);
    }

    public void changeSettings() {
        Log.d("Sovellus", "Changed user settings " + UserList.getInstance().getCurrentUser().getName() + " Name " + selectedName + " Age " + selectedYear + " Gender " + selectedSex);
        UserList.getInstance().getCurrentUser().setSex(selectedSex);
        UserList.getInstance().getCurrentUser().setName(selectedName);
        UserList.getInstance().getCurrentUser().setYearOfBirth(selectedYear);

        Toast.makeText(thisActivity, "Tallennus onnistui!",
                Toast.LENGTH_SHORT).show();
    }
}

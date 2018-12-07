package com.example.matias.viewpagerwithtabs;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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


    Time today;

    /*
    * Käyttäjä kirjautuu sisään 1. kerran: ("isTimer" = false)
    *       -> napissa lukee "Aloita"
    *       -> Tekstikentässä ohjeet "Valitse aktiviteetti ja paina aloita"
    *
    * Käyttäjä painaa "ALOITA":
    *       -> ("isTimer" = true)
    *       -> Napissa lukee "Lopeta"
    *       -> Tekstikentässä "Aktiviteetti aloitettu "KELLONAIKA"
    *
    * Käyttäjä lähtee ruudusta ja tulee takaisin: ("isTimer" == true)
    *       -> Nappulassa lukee "lopeta"
    *       -> Tekstikentässä "Aktiviteetti aloitettu "KELLONAIKA"
    *
    * Käyttäjä painaa lopeta:
    *      -> ("isTimer" = false)
    *       -> Nappulassa lukee "Aloita"
    *       -> Tekstikentässä "Aktiviteetin kesto: "KESTO", lisää uusi aktiviteetti valitsemalla ja painamalla aloita
    * */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Log.d("frag", "onCreateView");
        v = inflater.inflate(R.layout.fragment_input, container, false);
        Button btnOK = (Button) v.findViewById(R.id.addInput);

        v.findViewById(R.id.addInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("frag", "klick");

            }
        });

        pref = this.getActivity().getSharedPreferences("time", Activity.MODE_PRIVATE);
        isTimer = pref.getBoolean("isTimer", false);
        prefEditor = pref.edit();
        time = v.findViewById(R.id.timeView);

        ArrayList list = ActionList.getInstance().getActivities();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, list);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinnerActions);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        start = v.findViewById(R.id.startButton);

        today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isTimer == false) {

                    startTime = System.currentTimeMillis()/1000/60;
                    Log.d("starttime", String.valueOf(startTime));

                    time.setText("Aloitusaika: \n" + today.hour + ":" + today.minute);
                    prefEditor.putString("startInfo", "Aloitusaika: \n" + today.hour + ":" + today.minute );

                    prefEditor.putLong("starttime", startTime);

                    prefEditor.putInt("startimeActivity", selectedAction);

                    isTimer = true;
                    start.setText("Lopeta");
                    prefEditor.putBoolean("isTimer", true);
                    prefEditor.apply();

                }else if (isTimer == true) {
                    Long startTimeMemory = pref.getLong("starttime", 0);
                    int savedActivity = pref.getInt("starttimeActivity", 0);

                    endTime = System.currentTimeMillis() / 1000 / 60;
                    Log.d("endtime", String.valueOf(endTime));

                    time.setText("Lopetusaika: " + Long.toString(endTime));

                    isTimer = false;
                    prefEditor.putBoolean("isTimer", false);
                    start.setText("Aloita");

                    Long dtime = endTime - startTimeMemory;

                    time.setText("Aktiviteetin kesto: " + Long.toString(dtime) + " min");
                    int sendTime = Math.toIntExact(dtime) / 1000 / 60;

                    ActionList.getInstance().addAction(savedActivity, sendTime);
                    Toast.makeText(getContext(), "Aktiviteetti lisätty",
                            Toast.LENGTH_SHORT).show();

                    prefEditor.apply();

                }
            }
        });

        //GetCurrentTimeInMillis;

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });

        v.findViewById(R.id.addInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActionList.getInstance().addAction(selectedAction, 110);

                Toast.makeText(getContext(), "Aktiviteetti lisätty",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onResume() {

        super.onResume();

        TextView hello = v.findViewById(R.id.tvHello);
        hello.setText("Hei " + UserList.getInstance().getCurrentUser().getName() + "!");
        //time.setText(hours + ":" + minutes);

        isTimer = pref.getBoolean("isTimer", false);

        Log.d("time", Boolean.toString(isTimer));

        if(isTimer==true){

            start.setText("Lopeta");
            time.setText(pref.getString("startInfo", "e"));

        }

        else if(isTimer==false){

            start.setText("Aloita");
            time.setText("Valitse uusi aktiviteetti ja aloita se painamalla \"aloita\"");

        }

    }
}
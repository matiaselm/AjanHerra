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
    private boolean click;

    SimpleDateFormat dateFormatHour;
    DateTimeFormatter dateTimeFormatter;
    Date date;

    private Long startTime;
    private Long endTime;
    SharedPreferences pref;
    SharedPreferences visit;
    SharedPreferences.Editor prefEditor;
    SharedPreferences.Editor visitEditor;

    Time today;

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
        visit = this.getActivity().getSharedPreferences("visit", Activity.MODE_PRIVATE);

        visitEditor = visit.edit();
        prefEditor = pref.edit();

        click = false;
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


                if(click == false) {

                    startTime = System.currentTimeMillis()/1000/60;
                    Log.d("starttime", String.valueOf(startTime));

                    time.setText("Aloitusaika: \n" + today.hour + ":" + today.minute);

                    prefEditor.putLong("starttime", startTime);
                    prefEditor.putInt("startimeActivity", selectedAction);
                    prefEditor.putBoolean("visit", true);
                    prefEditor.apply();

                    Log.d("time","Click = true");
                    start.setText("Lopeta");
                    click = true;

                }else{

                    Long startTimeMemory = pref.getLong("starttime", 0);
                    int savedActivity = pref.getInt("starttimeActivity", 0);

                    endTime = System.currentTimeMillis()/1000/60;
                    Log.d("endtime", String.valueOf(endTime));

                    time.setText("Lopetusaika: " + Long.toString(endTime));
                    click = false;
                    prefEditor.putBoolean("visit", false);
                    Log.d("time","Click = false");
                    start.setText("Aloita");

                    Long dtime = endTime - startTimeMemory;

                    time.setText("Aktiviteetin kesto: " + Long.toString(dtime) +" min");
                    int sendTime =  Math.toIntExact(dtime) / 1000/60;

                    ActionList.getInstance().addAction(savedActivity, sendTime);
                    Toast.makeText(getContext(), "Aktiviteetti lisätty",
                            Toast.LENGTH_SHORT).show();

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

        Boolean isTimer = pref.getBoolean("visit", false);

       /* if(isTimer==true){
            time.setText("Aloitusaika: \n" + pref.getString("starttime", String.valueOf(0)));
            start.setText("Lopeta");
        }else{
            return;
        }*/
    }
}
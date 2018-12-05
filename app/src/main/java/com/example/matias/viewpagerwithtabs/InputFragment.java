package com.example.matias.viewpagerwithtabs;


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
    private int hours;
    private int minutes;
    private int endMinutes;
    private Button start;
    private boolean click;
    SimpleDateFormat dateFormat;
    //TimeDifference timeDifference;
    Date date;


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

       // timeDifference = new TimeDifference();

        click = false;

        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        time = v.findViewById(R.id.timeView);

        ArrayList list = ActionList.getInstance().getActivities();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, list);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinnerActions);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        v.findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(click == false) {

                    date = new Date();
                    String startTime = dateFormat.format(date);
                    Log.d("starttime", startTime);

                    time.setText("Aloitusaika: " + startTime);

                    click = true;
                    Log.d("time","Click = true");

                }else{

                    date = new Date();
                    String endTime = dateFormat.format(date);
                    Log.d("endtime", endTime);

                    time.setText("Lopetusaika: " + endTime);
                    click = false;

                    Log.d("time","Click = false");

                }


            }
        });


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
                Log.d("Sovellus", "Selected " + Integer.toString(selectedAction));

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

        time.setText(hours + ":" + minutes);

    }
}



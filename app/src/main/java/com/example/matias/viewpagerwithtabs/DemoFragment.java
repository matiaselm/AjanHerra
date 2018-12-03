package com.example.matias.viewpagerwithtabs;


import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends Fragment {
    private TextView textView;


    public DemoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int fragmentNr = (getArguments().getInt("position"));

        if (fragmentNr == 0){
            View view = inflater.inflate(R.layout.fragment_demo, container, false);
            textView = view.findViewById(R.id.txt_display);
            textView.setText("page 001");
            return view;
        }
        else if (fragmentNr == 1){
            View view = inflater.inflate(R.layout.fragment_demo, container, false);
            //textView = view.findViewById(R.id.txt_display);
            //textView.setText("page 002");
            return view;
        }
        else{
            View view = inflater.inflate(R.layout.fragment_demo, container, false);
            textView = view.findViewById(R.id.txt_display);
            textView.setText("page 003");
            return view;
        }

    }
}
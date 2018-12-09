package com.example.matias.viewpagerwithtabs.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.matias.viewpagerwithtabs.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrontFragment extends Fragment {


    public FrontFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_front, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("Sovellus", "onResume output");
        //Do not know why this crash???
       //closeKeyboard();
    }


    public void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}

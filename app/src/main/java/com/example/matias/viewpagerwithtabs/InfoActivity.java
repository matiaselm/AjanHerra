package com.example.matias.viewpagerwithtabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ArrayList list = ActionList.getInstance().getActivities();

        Bundle b = getIntent().getExtras();
        int numero = b.getInt("itempos", -1);
        // updateUI();
        TextView name = findViewById(R.id.name);
        name.setText(ActionList.getInstance().getActivities().get(numero).getType());
        TextView additional = findViewById(R.id.additional);
        additional.setText(ActionList.getInstance().getActivities().get(numero).getInfo());
        TextView start = findViewById(R.id.start);
        start.setText(Double.toString(ActionList.getInstance().getActivities().get(numero).getTime()));
        TextView end = findViewById(R.id.end);
        end.setText(ActionList.getInstance().getActivities().get(numero).toString());
    }
}

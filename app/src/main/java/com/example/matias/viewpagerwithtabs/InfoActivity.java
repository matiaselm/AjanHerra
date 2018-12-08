package com.example.matias.viewpagerwithtabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar toolbar = findViewById(R.id.returnBar);
        setSupportActionBar(toolbar);

        ArrayList list = ActionList.getInstance().getActivities();

        Bundle b = getIntent().getExtras();
        int numero = b.getInt("itempos", -1);
        // updateUI();
        TextView name = findViewById(R.id.name);
        name.setText(ActionList.getInstance().getActivities().get(numero).getType());

        TextView reference = findViewById(R.id.reference);
        reference.setText(ActionList.getInstance().getActivities().get(numero).getTimeReference());

        TextView average = findViewById(R.id.average);
        average.setText(ActionList.getInstance().getActivities().get(numero).getTimeAverage());

        TextView result = findViewById(R.id.result);
        result.setText(ActionList.getInstance().getActivities().get(numero).getTimeResult());

        TextView additional = findViewById(R.id.additional);
        additional.setText(ActionList.getInstance().getActivities().get(numero).getDescription());
    }

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
                Toast.makeText(this, "Asetukset",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_info:
                Toast.makeText(this, "Sovelluksen tiedot",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_logout:
                Toast.makeText(this, "Kirjauduit ulos",
                        Toast.LENGTH_SHORT).show();
                break;

            default:
                //error
        }
        return super.onOptionsItemSelected(item);
    }
}

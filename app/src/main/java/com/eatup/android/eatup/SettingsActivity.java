package com.eatup.android.eatup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsActivity extends ActionBarActivity {

    private EditText etDist;

    private Button btSaveSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initSettings();
        etDist = (EditText) findViewById(R.id.etDist);
    }

    private void initSettings() {
        btSaveSetting = (Button) findViewById(R.id.btSaveSetting);
        btSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etDist.getText().toString().isEmpty()) {
                    LoginActivity.currentProfile.setDistance(Double.parseDouble(etDist.getText().toString()));
                    //TODO: Check to see what time it is and call LunchTime or NotLunchTime
                    Intent i = new Intent(getApplicationContext(), LunchTimeActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a distance!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

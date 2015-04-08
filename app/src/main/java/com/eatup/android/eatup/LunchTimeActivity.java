package com.eatup.android.eatup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;


public class LunchTimeActivity extends ActionBarActivity {
    private Button btYes;
    private Button btNotToday;
    private TextView tvGPSloc;
    private double latitude;
    private double longitude;
    ParseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_time);
        initSettings();
        locateGPS();

    }

    private void locateGPS() {
        GPSTracker gps;
        gps = new GPSTracker(this);


        if(gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            ParseGeoPoint point = new ParseGeoPoint(latitude, longitude);
            currentUser.put("location",point);
            currentUser.put("paired", "no");
            currentUser.saveInBackground();


            tvGPSloc.setText(Double.toString(latitude) + "," + Double.toString(longitude));
        } else {
            gps.showSettingsAlert();
        }
    }

    private void initSettings() {
        btYes = (Button) findViewById(R.id.btYes);
        btNotToday = (Button) findViewById(R.id.btNotToday);
        tvGPSloc = (TextView) findViewById(R.id.tvGPSloc);
        currentUser = ParseUser.getCurrentUser();

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.put("lunching","yes");
                currentUser.saveInBackground();
                Intent yes = new Intent(getApplicationContext(),LunchingActivity.class);
                startActivity(yes);
            }
        });

        btNotToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.put("lunching","no");
                currentUser.saveInBackground();
                Intent notToday = new Intent(getApplicationContext(), NotLunch.class);
                startActivity(notToday);

            }
        });
    }

    // Current User profile access
    public void onProfileView (MenuItem mi){
        // Launch the profile view

        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lunch_time, menu);
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

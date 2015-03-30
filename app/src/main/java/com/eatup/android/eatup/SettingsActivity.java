package com.eatup.android.eatup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;


public class SettingsActivity extends ActionBarActivity {

    private LinkedInClient client;
    private Button btSaveSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getLIResponse();
        initSettings();

    }

    private void initSettings() {
        btSaveSetting = (Button) findViewById(R.id.btSaveSetting);
        btSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LunchTimeActivity.class);
                startActivity(i);
            }
        });
    }

    //Send an API request to get the timeline JSON
    //Fill the listview by creating the tweet objects from the JSON
    private void getLIResponse() {
        client = RestApplication.getLIClient();
        client.getLIProfile(new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                //DESERIALIZE JSON
                //CREATE MODELS
                //LOAD THE MODEL DATA INTO A LIST VIEW
                Log.d("DEBUG", json.toString());
            }
            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG PopulateTimeline", errorResponse.toString());
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

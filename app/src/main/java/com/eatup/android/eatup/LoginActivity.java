package com.eatup.android.eatup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.eatup.android.eatup.model.Profile;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;

public class LoginActivity extends OAuthLoginActionBarActivity<LinkedInClient> {

    private LinkedInClient liClient;
    public static Profile currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    // OAuth authenticated successfully, launch primary authenticated activity
    // i.e Display application "homepage"
    @Override
    public void onLoginSuccess() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0:00"));
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hour2 = cal.get(Calendar.HOUR_OF_DAY);
        long seconds = (hour2 *60 *60) + (minute * 60) + second;

        if (41400 > seconds && seconds > 39600 ) {
            Intent j = new Intent(this, LunchTimeActivity.class);
            startActivity(j);
        }
        else {
            Intent i = new Intent(this, NotLunchTimeActivity.class);
            startActivity(i);
        }

        getLIResponse();

    }

    private void getLIResponse() {
        liClient = RestApplication.getLIClient();
        liClient.getLIProfile(new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                //DESERIALIZE JSON
                //CREATE MODELS
                //LOAD THE MODEL DATA INTO A LIST VIEW
                currentProfile = Profile.fromJSONObject(json);
                Log.d("DEBUG", json.toString());
            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG PopulateTimeline", errorResponse.toString());
            }
        });
    }

    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void loginToRest(View view) {
        getClient().connect();
    }

}

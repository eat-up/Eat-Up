package com.eatup.android.eatup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.eatup.android.eatup.model.Profile;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;

public class LoginActivity extends OAuthLoginActionBarActivity<LinkedInClient> {

    private LinkedInClient liClient;
    public static Profile currentProfile;
    private static String sUserId;


    private String userName;
    private String email;

    public static final String YOUR_APPLICATION_ID = "hL9rXd1CIDxDnKT1i3RAQUpdN7Eze6OVsaz8y9ga";
    public static final String YOUR_CLIENT_KEY = "K1J8q09PibKi64Z7acQNQjRJVj4HrURY3LoJNSDF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        pinningProfile();
//        checkParseAuth();

        // Register your parse models
//        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Profile.class);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
    }


//    private void checkParseAuth() {
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        if (currentUser != null) {
//            startWithCurrentUser();
//        } else {
//
//        }
//
//    }

    // Get the userId from the cached currentUser object
    private void startWithCurrentUser() {
        sUserId = ParseUser.getCurrentUser().getObjectId();
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


        getLIResponse();


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
//            if (41400 > seconds && seconds > 39600 ) {
                Log.d("currentUser", currentUser.toString());
                Intent j = new Intent(this, LunchTimeActivity.class);
                startActivity(j);
//            } else {
//                Intent i = new Intent(this, NotLunchTimeActivity.class);
//                startActivity(i);
//            }
        } else {
            // show the signup or login screen
            Log.d("DEBUG", "Current User not Found!!!!!");
        }

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
                String uId = currentProfile.getUid();
                ParseUser.logInInBackground(uId ,"BOOM", new LogInCallback() {
                            @Override
                            public void done(ParseUser parseUser, ParseException e) {
                                if (parseUser != null) {
                                    // Hooray! The user is logged in.
                                } else {
                                    // Signup failed. Look at the ParseException to see what happened.
                                }
                            }
                        }
                );
                Log.d("Deeeeg",currentProfile.getUid());
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

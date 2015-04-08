package com.eatup.android.eatup;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class LunchingActivity extends ActionBarActivity {
    private TextView tvTimeCount;
    ParseUser currentUser;
    ParseQuery<ParseObject> query;

    private String partnerUID;
    private String eatUpPartner;
    private String profilePic;
    private String industry;

    private double averLat;
    private double averLong;

    private Boolean paired;

    private static BroadcastReceiver tickReceiver;
    private static final String FORMAT = "%02d:%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunching);
        initSettings();
        try {
            checkPartner();
            paired = true;
            currentUser.put("paired","yes");
            currentUser.saveInBackground();
        } catch (ParseException e) {
            Intent fallback = new Intent(getApplicationContext(),NotLunchTimeActivity.class);
            startActivity(fallback);
            e.printStackTrace();
        }
        timeTick();


    }

    private void checkPartner() throws ParseException {
        query = ParseQuery.getQuery("_User");
        query.whereNear("location",(ParseGeoPoint)currentUser.get("location"));
        query.whereEqualTo("lunching", "yes");
        query.whereEqualTo("paired","no");
        query.setLimit(2);

        //partnerUID = query.find().get(1).get("username").toString();
        partnerUID = query.find().get(1).getObjectId();
        eatUpPartner = query.find().get(1).get("name").toString();
        profilePic = query.find().get(1).get("pictureUrl").toString();
        industry = query.find().get(1).get("industry").toString();

        averLat = (query.find().get(0).getParseGeoPoint("location").getLatitude() +
                query.find().get(1).getParseGeoPoint("location").getLatitude()) / 2;
        averLong = (query.find().get(0).getParseGeoPoint("location").getLongitude() +
                query.find().get(1).getParseGeoPoint("location").getLongitude()) / 2;


        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("username", partnerUID);
        params.put("myusername", currentUser.getObjectId());
        //params.put("paired", "no");
        ParseCloud.callFunctionInBackground("getPaired", params, new FunctionCallback<Object>() {
            public void done(Object someTalk, ParseException e) {
                if (e == null) {
                    Log.d("it's herererere", "when here success!!!!");
                } else {
                    Log.d ("it's hereher", e.toString());
                }
            }
        });



    }

    private void initSettings() {
        tvTimeCount = (TextView) findViewById(R.id.tvLunchTime);
        currentUser = ParseUser.getCurrentUser();
    }

    private void timeTick() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0:00"));
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hour2 = cal.get(Calendar.HOUR_OF_DAY);

        long millisecond = ((hour2 *60 *60) + (minute * 60) + second) * 1000;
        long timeleft = 41400000 - millisecond;

        new CountDownTimer(10000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                tvTimeCount.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish()
            {
                if (currentUser.get("lunching").toString().equalsIgnoreCase("yes") && paired) {
                    paired = false;
                    Intent pairUp = new Intent(getApplicationContext(), MatchActivity.class);
                    Bundle b = new Bundle();
                    b.putString("partnerUID", partnerUID);
                    b.putString("partner", eatUpPartner);
                    b.putString("profilePic", profilePic);
                    b.putDouble("averLat", averLat);
                    b.putDouble("averLong", averLong);
                    b.putString("industry",industry);
                    pairUp.putExtras(b);

                    startActivity(pairUp);
                } else {
                    Intent i = new Intent(getApplicationContext(),NotLunchTimeActivity.class);
                    startActivity(i);
                }

            }
        }.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lunching, menu);
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

    @Override
    public void onStop()
    {
        super.onStop();
        //unregister broadcast receiver.
        if(tickReceiver!=null)
            unregisterReceiver(tickReceiver);
    }

    @Override
    public void onBackPressed() {
        paired = false;
        currentUser.put("lunching","no");
        currentUser.put("paired", "no");
        currentUser.saveInBackground();
        ParseUser.logInInBackground(partnerUID, "BOOM", new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            // Hooray! The user is logged in.
                            parseUser.put("paired", "no");
                            parseUser.saveInBackground();

                        } else {
                            // Signup failed. Look at the ParseException to see what happened.
                        }
                    }
                }
        );
        finish();
    }
}

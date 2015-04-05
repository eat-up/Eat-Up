package com.eatup.android.eatup;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class NotLunch extends ActionBarActivity {
    private Button btCanLunch;
    private TextView tvNotTimeCount;
    private TextView tvTest;
    private static BroadcastReceiver tickReceiver;
    private static final String FORMAT = "%02d:%02d:%02d";
    ParseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_lunch);
        initSettings();
        timeTick();
    }

    private void initSettings() {
        btCanLunch = (Button) findViewById(R.id.btCanLunch);
        btCanLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                currentUser.put("lunching","yes");
                currentUser.saveInBackground();
                Intent i = new Intent(getApplicationContext(), LunchingActivity.class);
                startActivity(i);
            }
        });
        tvNotTimeCount = (TextView) findViewById(R.id.tvNotTimeCount);


    }

    private void timeTick() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0:00"));
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hour2 = cal.get(Calendar.HOUR_OF_DAY);

        long millisecond = ((hour2 *60 *60) + (minute * 60) + second) * 1000;
        long timeleft = 41400000 - millisecond;

        new CountDownTimer(timeleft, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                tvNotTimeCount.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                tvNotTimeCount.setText("done!");
            }
        }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_not_lunch, menu);
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

package com.eatup.android.eatup;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MatchActivity extends ActionBarActivity {

    private YelpAPI yelpClient;

    private static final String CONSUMER_KEY = "3sWBijFfbUR0GqK8uJ7O4w";
    private static final String CONSUMER_SECRET = "lMvTuK3dadn_ZxeTPgKUQBpZsDQ";
    private static final String TOKEN = "8Nf6hP8QYr1LX4ijGQxdaExZg3BrVQzX";
    private static final String TOKEN_SECRET = "X8h0eofBYRxkX53860LspFAS49E";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        getRestaurants();
    }

    private void getRestaurants() {
        yelpClient = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    yelpClient.queryAPI(yelpClient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match, menu);
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

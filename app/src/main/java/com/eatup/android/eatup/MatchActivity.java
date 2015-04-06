package com.eatup.android.eatup;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eatup.android.eatup.model.BusinessArrayAdapter;
import com.eatup.android.eatup.model.Businesses;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MatchActivity extends ActionBarActivity {

    private static YelpClient yelpClient = null;
    public static Context context;
    private ArrayList<Businesses> businesseses;
    private static BusinessArrayAdapter aBusinesseses;
    private ListView lvBusinesses;

    //private YelpAPI2 yelpClient;
    private static final String CONSUMER_KEY = "3sWBijFfbUR0GqK8uJ7O4w";
    private static final String CONSUMER_SECRET = "lMvTuK3dadn_ZxeTPgKUQBpZsDQ";
    private static final String TOKEN = "8Nf6hP8QYr1LX4ijGQxdaExZg3BrVQzX";
    private static final String TOKEN_SECRET = "X8h0eofBYRxkX53860LspFAS49E";

    String partnerUID;
    String profilePic;
    String partnerName;
    Double averLat;
    Double averLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Bundle b = getIntent().getExtras();
        partnerUID = b.getString("partnerUID");
        averLat = b.getDouble("averLat");
        averLong = b.getDouble("averLong");
        partnerName = b.getString("partner");
        profilePic = b.getString("profilePic", profilePic);
        //getRestaurants();
        Log.d("partneeer!!", partnerUID);
        Log.d("partneeer!2222", partnerName);
        Log.d("partneeer!3333", profilePic);

        //Set User Profile
        TextView tvFullName = (TextView) findViewById(R.id.tvFullName);
        tvFullName.setText(partnerName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        tvTagline.setText("Developer");

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        //ivProfileImage.setImageResource();
        Picasso.with(this).load(profilePic).into(ivProfileImage);


        // Get yelp restaurants and display in the list  view
        lvBusinesses = (ListView) findViewById(R.id.lvBusinesses);

        // Create the arrayList
        businesseses = new ArrayList<>();

        // Construct the adapter
        aBusinesseses = new BusinessArrayAdapter(this, businesseses);

        // Connect listview to adapter
        lvBusinesses.setAdapter(aBusinesseses);

        yelpClient = new YelpClient(this);
        populateBusinesses("restaurants", averLat, averLong);

    }

    private void populateBusinesses(String term, Double lat, Double lng){
        new ReadYelpJSONFeedTask().execute(term,lat,lng);

    }

    private class ReadYelpJSONFeedTask extends AsyncTask<Object, Void, String> {
        protected String doInBackground(Object... param) {

            String term = (String)param[0];
            Double lat = (Double) param[1];
            Double lng = (Double) param[2];

            String response=null;
            response = yelpClient.getBusiness(term,lat,lng);
            Log.v("readJSONFeed  response ", response);
            return response;
            //return readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result) {

            Log.v("Result ", result);

            try {


                JSONObject o1 = new JSONObject(result);
                JSONArray businesses = o1.getJSONArray("businesses");

                aBusinesseses.addAll(Businesses.fromJSONArray(businesses));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("onPostExecute", e.getLocalizedMessage());

            }
            // try

        } // post
    } //read

    /*
    private void getRestaurants() {
        yelpClient = new YelpAPI2(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
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
    */

    public void openChat(View view) {
        Intent i = new Intent(this, ChatActivity.class);
        i.putExtra("matchPic", profilePic);
        startActivity(i);
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

    @Override
    public void onBackPressed() {
    }
}

package com.eatup.android.eatup;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends ActionBarActivity {

    ParseUser currentUser;
    ParseQuery<ParseObject> query;

    private String userName;
    private String profilePic;
    private String industry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        try {
            populateProfileHeader();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void populateProfileHeader() throws ParseException {

        currentUser = ParseUser.getCurrentUser();
        String userid=null;

        TextView tvName = (TextView) findViewById(R.id.tvFullName);
        TextView tvIndustry = (TextView) findViewById(R.id.tvIndustry);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        query = ParseQuery.getQuery("_User");

        userid=currentUser.getUsername();
        Log.d("Debug username:id ", userid);
        query.whereEqualTo("username",currentUser.getUsername());
        query.setLimit(2);

        userName = query.find().get(0).get("name").toString();
        profilePic = query.find().get(0).get("pictureUrl").toString();
        industry = query.find().get(0).get("industry").toString();

        tvName.setText(userName);
        tvIndustry.setText(industry);

        Picasso.with(this).load(profilePic).into(ivProfileImage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

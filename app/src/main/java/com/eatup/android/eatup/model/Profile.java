package com.eatup.android.eatup.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ariel on 4/2/15.
 */
@ParseClassName("Profile")
public class Profile extends ParseObject {

    public Profile() {
        super();
    }

    private String pictureUrl;
    private String name;
    private String industry;
    private double distance;
    private String uid;
//    This activity may not be created for the final version
//    private Histroy history;
    private Double[] coordinates;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public static Profile fromJSONObject(JSONObject jsonObject) {
        Profile profile = new Profile();
        // Create the ParseUser
        ParseUser parseProfile = new ParseUser();
        try {
            profile.name = jsonObject.getString("firstName") + " " +jsonObject.getString("lastName");
            profile.industry = jsonObject.getString("industry");
            profile.pictureUrl = jsonObject.getString("pictureUrl");
            profile.uid = jsonObject.getString("id");

            parseProfile.setUsername(profile.uid);
            parseProfile.setPassword("BOOM");
            //Setup custom properties
            parseProfile.put("industry", profile.industry);
            parseProfile.put("pictureUrl", profile.pictureUrl);
            parseProfile.put("name", profile.name);
            parseProfile.signUpInBackground();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profile;
    }
}

package com.eatup.android.eatup.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rbhavsar on 4/1/2015.
 */
public class Businesses {

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public static String getRatingImageUrl() {
        return ratingImageUrl;
    }

    public void setRatingImageUrl(String ratingImageUrl) {
        this.ratingImageUrl = ratingImageUrl;
    }

    //List out the attributes
    private String businessName;
    private static String ratingImageUrl;
    private String mobile;
    private String categories;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

    // Deserialize the JSON
    // create method to convert business.fromJson({..}") => <business>

    public static Businesses fromJSON(JSONObject jsonObject) {
        Businesses business = new Businesses();
        String cat= null;
        String category="Food";
        String phone = null;

        // Extract the values from the json, store them

        try {

            business.businessName = jsonObject.getString("name");
            business.ratingImageUrl = jsonObject.getString("rating_img_url");
            business.mobile = jsonObject.getString("mobile_url");
            //phone = jsonObject.getString("display_phone");
            try {
                cat = jsonObject.getString("categories");
                int ind= cat.indexOf(',');
                category=cat.substring(3,ind-1);
            }catch(Exception e) {
                category = "Food";
            }
            business.categories = category;
            try {
                phone = jsonObject.getString("display_phone");
            }catch(Exception e){
                phone = jsonObject.getString("phone");
            }
            business.phone=phone;

        } catch (JSONException e ){
            e.printStackTrace();

        }

        // Return the business object
        return business;

    }


    // Pass Json array and ourtout us list of businesss
    public static ArrayList<Businesses> fromJSONArray(JSONArray jsonArray) {

        ArrayList<Businesses> businesss = new ArrayList<>();

        // Iterrate the json array and create businesss
        for (int i=0; i< jsonArray.length() ; i++){

            try {
                JSONObject businessJson = jsonArray.getJSONObject(i);

                Businesses business = Businesses.fromJSON(businessJson);

                if (business != null ){
                    businesss.add(business);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }

        // return the finished list
        return businesss;

    }


}

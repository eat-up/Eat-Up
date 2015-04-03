package com.eatup.android.eatup;

import android.content.Context;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     LinkedInClient client = RestApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class RestApplication extends com.activeandroid.app.Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        RestApplication.context = this;
    }

    public static LinkedInClient getLIClient() {
        return (LinkedInClient) LinkedInClient.getInstance(LinkedInClient.class, RestApplication.context);
    }



//    public static YelpAPI getYelpClient() {
//        return (YelpAPI) YelpAPI.getInstance(YelpAPI.class, RestApplication.context);
//    }
}
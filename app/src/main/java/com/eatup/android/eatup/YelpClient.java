//package com.eatup.android.eatup;
//
//
//import android.content.Context;
//
//import com.codepath.oauth.OAuthBaseClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//
//import org.scribe.builder.api.Api;
//
///*
// *
// * This is the object responsible for communicating with a REST API.
// * Specify the constants below to change the API being communicated with.
// * See a full list of supported API classes:
// *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
// * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
// * Add methods for each relevant endpoint in the API.
// *
// * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
// *
// */
//public class YelpClient extends OAuthBaseClient {
//    public static final Class<? extends Api> REST_API_CLASS = YelpAPI.class; // Change this
//    public static final String REST_URL = "https://api.yelp.com"; // Change this, base API URL
//    public static final String REST_CONSUMER_KEY = "3sWBijFfbUR0GqK8uJ7O4w";       // Change this
//    public static final String REST_CONSUMER_SECRET = "lMvTuK3dadn_ZxeTPgKUQBpZsDQ"; // Change this
//    public static final String REST_CALLBACK_URL = "oauth://eatup"; // Change this (here and in manifest)
//
//
//    private static final String TOKEN = "8Nf6hP8QYr1LX4ijGQxdaExZg3BrVQzX";
//    private static final String TOKEN_SECRET = "X8h0eofBYRxkX53860LspFAS49E";
//
//
//    public YelpClient(Context context) {
//        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
//    }
//
//
//    // CHANGE THIS
//    // DEFINE METHODS for different API endpoints here
//    public void getYelpRestaurant(AsyncHttpResponseHandler handler) {
//        String apiUrl = getApiUrl("v2/search");
//        // Can specify query string params directly or through RequestParams.
//        client.get(apiUrl, null, handler);
//    }
//
//	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
//	 * 	  i.e getApiUrl("statuses/home_timeline.json");
//	 * 2. Define the parameters to pass to the request (query or body)
//	 *    i.e RequestParams params = new RequestParams("foo", "bar");
//	 * 3. Define the request method and make a call to the client
//	 *    i.e client.get(apiUrl, params, handler);
//	 *    i.e client.post(apiUrl, params, handler);
//	 */
//}
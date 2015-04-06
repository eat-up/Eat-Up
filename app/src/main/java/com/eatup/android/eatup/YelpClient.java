package com.eatup.android.eatup;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Created by rbhavsar on 4/5/2015.
 */
public class YelpClient extends OAuthBaseClient {

    OAuthService service;
    Token accessToken;

    public static final Class<? extends Api> REST_API_CLASS = YelpApi.class;

    public static final String REST_URL = "api.yelp.com"; // Change this, base API URL
    public static final String REST_CALLBACK_URL = "oauth://eatupyelp"; // Change this (here and in manifest)

    private static final String REST_CONSUMER_KEY = "3sWBijFfbUR0GqK8uJ7O4w";
    private static final String REST_CONSUMER_SECRET = "lMvTuK3dadn_ZxeTPgKUQBpZsDQ";
    private static final String REST_TOKEN = "8Nf6hP8QYr1LX4ijGQxdaExZg3BrVQzX";
    private static final String REST_TOKEN_SECRET = "X8h0eofBYRxkX53860LspFAS49E";

    public YelpClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        this.service = new ServiceBuilder().provider(YelpApi.class).apiKey(REST_CONSUMER_KEY).apiSecret(REST_CONSUMER_SECRET).build();
        this.accessToken = new Token(REST_TOKEN, REST_TOKEN_SECRET);


    }

    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

    public String getBusiness(String term, double latitude, double longitude) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("limit", "3");
        request.addQuerystringParameter("ll", latitude + "," + longitude);
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        String s = response.toString();
        return response.getBody();

    }


}

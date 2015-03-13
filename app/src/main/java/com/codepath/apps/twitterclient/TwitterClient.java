package com.codepath.apps.twitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.apps.twitterclient.fragments.TweetsListFragment;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.DecimalFormat;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "dVuV2N0PHWMfKWpnRxfAyBv1W";
	public static final String REST_CONSUMER_SECRET = "B19oPTUcwYZJPEccnEtqxMxs6GeaGmDPaadKuSQp3Z5XjcMeaw";
	public static final String REST_CALLBACK_URL = "oauth://zacktweetr"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

    public void getHomeTimeline(TweetsListFragment.PopulateOption option, Long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();

        params.put("count", 25);
        if (tweetId != null) {
            if (option == TweetsListFragment.PopulateOption.POPULATE_BOTTOM) {
                // To populate the bottom, we want older tweets with lower IDs.
                params.put("max_id", new DecimalFormat("#").format(tweetId - 1));
            } else if (option == TweetsListFragment.PopulateOption.POPULATE_TOP) {
                // To populate the top, we want newer tweets with higher IDs.
                params.put("since_id", new DecimalFormat("#").format(tweetId + 1));
            }
        }

        getClient().get(apiUrl, params, handler);
    }

    public void getMentionsTimeline(TweetsListFragment.PopulateOption option, Long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();

        params.put("count", 25);
        if (tweetId != null) {
            if (option == TweetsListFragment.PopulateOption.POPULATE_BOTTOM) {
                // To populate the bottom, we want older tweets with lower IDs.
                params.put("max_id", new DecimalFormat("#").format(tweetId - 1));
            } else if (option == TweetsListFragment.PopulateOption.POPULATE_TOP) {
                // To populate the top, we want newer tweets with higher IDs.
                params.put("since_id", new DecimalFormat("#").format(tweetId + 1));
            }
        }

        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(String screenName, TweetsListFragment.PopulateOption option, Long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        params.put("count", 25);
        if (tweetId != null) {
            if (option == TweetsListFragment.PopulateOption.POPULATE_BOTTOM) {
                // To populate the bottom, we want older tweets with lower IDs.
                params.put("max_id", new DecimalFormat("#").format(tweetId - 1));
            } else if (option == TweetsListFragment.PopulateOption.POPULATE_TOP) {
                // To populate the top, we want newer tweets with higher IDs.
                params.put("since_id", new DecimalFormat("#").format(tweetId + 1));
            }
        }
        getClient().get(apiUrl, params, handler);

    }

    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/verify_credentials.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }

    public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweet);
        getClient().post(apiUrl, params, handler);
    }

}
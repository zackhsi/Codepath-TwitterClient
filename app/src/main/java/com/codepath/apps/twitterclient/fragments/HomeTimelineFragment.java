package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitterclient.TwitterApplication;
import com.codepath.apps.twitterclient.TwitterClient;
import com.codepath.apps.twitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zackhsi on 3/11/15.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List<Tweet> getTweetsFromDatabase() {
        return Tweet.getHome();
    }

    @Override
    protected void getMoreTweets(PopulateOption option, Long tweetId, AsyncHttpResponseHandler handler) {
        client.getHomeTimeline(option, tweetId, handler);
    }

    @Override
    protected Long getMinId() {
        return Tweet.getMinHomeId();
    }

    @Override
    protected Long getMaxId() {
        return Tweet.getMaxHomeId();
    }
}

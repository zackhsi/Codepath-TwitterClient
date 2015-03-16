package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zackhsi on 3/11/15.
 */
public class UserTimelineFragment extends TweetsListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment fragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected List<Tweet> getTweetsFromDatabase() {
        return Tweet.getUserTimeline(getArguments().getString("screenName"));
    }

    @Override
    protected void getMoreTweets(PopulateOption option, Long tweetId, AsyncHttpResponseHandler handler) {
        client.getUserTimeline(getArguments().getString("screenName"), option, tweetId, handler);
    }

    @Override
    protected Long getMinId() {
        return Tweet.getMinUserId(getArguments().getString("screenName"));
    }

    @Override
    protected Long getMaxId() {
        return Tweet.getMaxUserId(getArguments().getString("screenName"));
    }
}

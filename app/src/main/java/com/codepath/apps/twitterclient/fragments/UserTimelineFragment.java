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
    protected List<Tweet> getTweets() {
        return Tweet.getUserTimeline(getArguments().getString("screenName"));
    }

    @Override
    protected void getTimeline(PopulateOption option, Long tweetId, AsyncHttpResponseHandler handler) {
        client.getHomeTimeline(option, tweetId, handler);
    }
}

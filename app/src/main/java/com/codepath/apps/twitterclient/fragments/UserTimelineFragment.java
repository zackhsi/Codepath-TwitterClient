package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitterclient.models.Tweet;
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

    protected void populateTimeline(final PopulateOption option) {
        Long tweetId = option == PopulateOption.POPULATE_BOTTOM ? Tweet.getMinId() : Tweet.getMaxId();
        String screenName = getArguments().getString("screenName");

        client.getUserTimeline(screenName, option, tweetId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());

                List<Tweet> tweets = Tweet.fromJSONArray(response, true);
                if (option == PopulateOption.POPULATE_TOP) {
                    addAll(0, tweets);
                } else if (option == PopulateOption.POPULATE_BOTTOM) {
                    addAll(tweets);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}

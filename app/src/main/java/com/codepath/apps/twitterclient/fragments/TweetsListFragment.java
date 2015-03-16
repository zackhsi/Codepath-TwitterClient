package com.codepath.apps.twitterclient.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TwitterApplication;
import com.codepath.apps.twitterclient.TwitterClient;
import com.codepath.apps.twitterclient.activities.ComposeActivity;
import com.codepath.apps.twitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitterclient.helpers.EndlessScrollListener;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.melnykov.fab.FloatingActionButton;
import com.yalantis.pulltorefresh.library.PullToRefreshView;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zackhsi on 3/11/15.
 */
public class TweetsListFragment extends Fragment {

    public static enum PopulateOption {
        POPULATE_TOP,
        POPULATE_BOTTOM,
    }

    protected ArrayList<Tweet> tweets;
    protected TweetsArrayAdapter aTweets;
    protected TwitterClient client;

    private ListView lvTweets;
    private FloatingActionButton fab;
    private int lastKnownFirst;
    private PullToRefreshView pullToRefreshView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), this.tweets);
        populateTimeline(PopulateOption.POPULATE_BOTTOM);
    }

    protected void refreshTweets() {
        tweets.clear();
        tweets.addAll(getTweetsFromDatabase());
        aTweets.notifyDataSetChanged();
    }

    protected List<Tweet> getTweetsFromDatabase() {
        // Subclasses override this to get relevant tweets
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ComposeActivity.class);
                startActivityForResult(i, ComposeActivity.COMPOSE_REQUEST_CODE);
            }
        });

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        setupTweetsListView();

        pullToRefreshView = (PullToRefreshView) v.findViewById(R.id.pull_to_refresh);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(PopulateOption.POPULATE_TOP);
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(false);
                    }
                }, 1);
            }
        });
        return v;
    }

    private void setupTweetsListView() {
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(PopulateOption.POPULATE_BOTTOM);
            }

            // https://github.com/makovkastar/FloatingActionButton/issues/99
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                if (firstVisibleItem > lastKnownFirst) {
                    fab.hide(true);
                } else if (firstVisibleItem < lastKnownFirst) {
                    fab.show(true);
                }
                lastKnownFirst = firstVisibleItem;
            }

        });
    }

    protected void populateTimeline(final PopulateOption option) {
        // TODO: get minId and maxId per timeline
        Long tweetId = option == PopulateOption.POPULATE_BOTTOM ? Tweet.getMinId() : Tweet.getMaxId();

        getMoreTweets(option, tweetId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                List<Tweet> tweets = Tweet.fromJSONArray(response, isMention());
                refreshTweets();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    protected Boolean isMention() {
        return false;
    }

    protected void getMoreTweets(TweetsListFragment.PopulateOption option, Long tweetId, AsyncHttpResponseHandler handler) {
        // Subclasses override this to get relevant tweets
        return;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ComposeActivity.COMPOSE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Tweet tweet = (Tweet) data.getSerializableExtra("tweet");
                aTweets.insert(tweet, 0);
            }
        }
    }

    public void addAll(List<Tweet> tweets) {
        tweets.addAll(tweets);
        refreshTweets();
    }

    public void addAll(int index, List<Tweet> tweets) {
        tweets.addAll(index, tweets);
        refreshTweets();
    }
}

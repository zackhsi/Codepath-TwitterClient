package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by zackhsi on 3/8/15.
 */
public class Tweet {
    private String createdAt;  // TODO use Date class
    private String text;

    private User user;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public static Tweet fromJSON(JSONObject json) {
        Tweet tweet = new Tweet();
        try {
            tweet.createdAt = json.getString("created_at");
            tweet.text = json.getString("text");
            tweet.user = User.fromJSON(json.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }
}

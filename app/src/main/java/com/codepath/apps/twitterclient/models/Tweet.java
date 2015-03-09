package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zackhsi on 3/8/15.
 */
public class Tweet {
    private Date createdAt;
    private String text;

    private User user;

    public Date getCreatedAt() {
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
            SimpleDateFormat format = new SimpleDateFormat("EEE LLL dd HH:mm:ss Z yyyy", Locale.ENGLISH);
            tweet.createdAt = format.parse(json.getString("created_at"));  // Wed Mar 03 19:37:35 +0000 2010
            tweet.text = json.getString("text");
            tweet.user = User.fromJSON(json.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tweet;
    }
}

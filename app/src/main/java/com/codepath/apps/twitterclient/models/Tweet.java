package com.codepath.apps.twitterclient.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zackhsi on 3/8/15.
 */

@Table(name = "Tweets")
public class Tweet extends Model implements Serializable {

    @Column(name = "twitterId")
    private Long twitterId;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "text")
    private String text;

    @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;

    @Column(name = "isMention")
    private Boolean isMention;

    public Tweet() {
        super();
    }

    public Long getTwitterId() {
        return twitterId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAtFromString(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        sf.setLenient(true);
        try {
            this.createdAt = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public static Tweet fromJSON(JSONObject jsonObject, Boolean isMention) {
        Tweet tweet = new Tweet();
        try {
            tweet.twitterId = jsonObject.getLong("id");
            tweet.setCreatedAtFromString(jsonObject.getString("created_at"));
            tweet.text = jsonObject.getString("text");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.isMention = isMention;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tweet.save();
        return tweet;
    }

    public static List<Tweet> fromJSONArray(JSONArray jsonArray, Boolean isMention) {

        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Tweet tweet = Tweet.fromJSON(jsonArray.getJSONObject(i), isMention);
                tweets.add(tweet);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tweets;
    }

    public static List<Tweet> getHome() {
        return new Select()
                .from(Tweet.class)
                .where("isMention = ?", false)
                .orderBy("twitterId DESC")
                .execute();
    }

    public static List<Tweet> getMentions() {
        return new Select()
                .from(Tweet.class)
                .where("isMention = ?", true)
                .orderBy("twitterId DESC")
                .execute();
    }

    public static Long getMinId() {
        Tweet tweet = new Select()
                .from(Tweet.class)
                .orderBy("twitterId ASC")
                .executeSingle();
        if (tweet == null) {
            return null;
        }
        return tweet.getTwitterId();
    }

    public static Long getMaxId() {
        Tweet tweet = new Select()
                .from(Tweet.class)
                .orderBy("twitterId DESC")
                .executeSingle();
        if (tweet == null) {
            return null;
        }
        return tweet.getTwitterId();
    }
}

package com.codepath.apps.twitterclient.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zackhsi on 3/8/15.
 */

@Table(name = "Tweets")
public class Tweet extends Model implements Serializable {

    @Column(name = "twitterId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private Long twitterId;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "text")
    private String text;

    @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;

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

    public static Tweet fromJSON(JSONObject json) {
        Tweet tweet = new Tweet();
        try {
            tweet.twitterId = json.getLong("id");
            tweet.setCreatedAtFromString(json.getString("created_at"));
            tweet.text = json.getString("text");
            tweet.user = User.fromJSON(json.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static List<Tweet> getAll() {
        return new Select()
                .from(Tweet.class)
                .execute();
    }

    public static Long getMaxId() {
        Tweet tweet = new Select()
                .from(Tweet.class)
                .orderBy("twitterId ASC")
                .executeSingle();
        if (tweet == null) {
            return null;
        }
        return tweet.getTwitterId();
    }
}

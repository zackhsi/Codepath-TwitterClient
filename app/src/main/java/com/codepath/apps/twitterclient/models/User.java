package com.codepath.apps.twitterclient.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zackhsi on 3/8/15.
 */
public class User extends Model implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "screenName")
    private String screenName;

    @Column(name = "profileImageURL")
    private String profileImageURL;

    @Column(name = "tagline")
    private String tagline;

    @Column(name = "followersCount")
    private int followersCount;

    @Column(name = "friendsCount")
    private int friendsCount;

    public User(){
        super();
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public static User fromJSON(JSONObject json) {
        User user = new User();
        try {
            user.name = json.getString("name");
            user.profileImageURL = json.getString("profile_image_url");
            user.screenName = json.getString("screen_name");
            user.tagline = json.getString("description");
            user.followersCount = json.getInt("followers_count");
            user.friendsCount = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        user.save();
        return user;
    }
}

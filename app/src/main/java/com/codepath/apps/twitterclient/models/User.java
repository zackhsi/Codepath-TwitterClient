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

    @Column(name = "profileImageURL")
    private String profileImageURL;

    public User(){
        super();
    }

    public String getName() {
        return name;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public static User fromJSON(JSONObject json) {
        User user = new User();
        try {
            user.name = json.getString("name");
            user.profileImageURL = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}

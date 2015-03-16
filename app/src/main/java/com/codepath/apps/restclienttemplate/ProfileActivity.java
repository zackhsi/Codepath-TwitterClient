package com.codepath.apps.restclienttemplate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.fragments.UserTimelineFragment;
import com.codepath.apps.twitterclient.models.User;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends ActionBarActivity {
    User user;

    TextView tvName;
    TextView tvTagline;
    TextView tvFollowers;
    TextView tvFollowing;
    ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = (User) getIntent().getSerializableExtra("user");

        findViews();

        getSupportActionBar().setTitle("@" + user.getScreenName());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#469AEA")));
        populateProfileHeader(user);

        if (savedInstanceState == null) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(user.getScreenName());

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    private void findViews() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvTagline = (TextView) findViewById(R.id.tvTagline);
        tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
    }

    private void populateProfileHeader(User user) {
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFriendsCount() + " Following");
        Picasso.with(this).load(user.getProfileImageURL()).into(ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }
}

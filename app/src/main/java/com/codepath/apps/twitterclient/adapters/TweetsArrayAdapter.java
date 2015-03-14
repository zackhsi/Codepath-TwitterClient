package com.codepath.apps.twitterclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.ProfileActivity;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.activities.TimelineActivity;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

/**
 * Created by zackhsi on 3/8/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        final ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUser = (TextView) convertView.findViewById(R.id.tvUser);
        TextView tvText = (TextView) convertView.findViewById(R.id.tvText);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);

        if (tweet.getUser() != null) {
            tvUser.setText(tweet.getUser().getName());
        }
        tvText.setText(tweet.getText());
        tvCreatedAt.setText(this.getRelativeTime(tweet.getCreatedAt()));

        ivProfileImage.setImageResource(android.R.color.transparent);
        if (tweet.getUser() != null) {
            Picasso.with(getContext()).load(tweet.getUser().getProfileImageURL()).into(ivProfileImage);
        }

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("user", tweet.getUser());
                getContext().startActivity(i);
            }
        });

        return convertView;
    }

    private String getRelativeTime(Date createdAt) {
        String fullString = DateUtils.getRelativeDateTimeString(
                getContext(),
                createdAt.getTime(),
                DateUtils.SECOND_IN_MILLIS,
                DateUtils.YEAR_IN_MILLIS, 0
        ).toString();
        String[] words = fullString.split(" ");
        String shortString = words[0] + words[1].charAt(0);
        return shortString;
    }
}

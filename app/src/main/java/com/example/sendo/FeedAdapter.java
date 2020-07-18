package com.example.sendo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FeedAdapter extends ArrayAdapter<String> {



    private int resourceLayout;
    private Context mContext;

    public FeedAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
        TextView userName = (TextView) v.findViewById(R.id.tweetUser);
        TextView tweet =  (TextView) v.findViewById(R.id.userstweet);
        userName.setText(FeedActivity.tweetusers.get(position).toString());
        tweet.setText(FeedActivity.feedList.get(position).toString());

        return v;
    }

}
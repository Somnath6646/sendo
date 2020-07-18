package com.example.sendo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    static List feedList;
    static List tweetusers;
    ListView listView;
    FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Intent intent = getIntent();
        feedList = new ArrayList();
        tweetusers = new ArrayList();
        listView = (ListView) findViewById(R.id.feedList);
        adapter = new FeedAdapter(this , R.layout.feedlist , feedList);
        listView.setAdapter(adapter);

        ParseQuery<ParseObject> query = new ParseQuery("Tweet");

        query.whereNotEqualTo("username" , ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    for (ParseObject object : objects) {
                        feedList.add(object.get("tweet"));
                        tweetusers.add(object.get("username"));
                        adapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Log.i("Showing feed"  , "Failed "+e.getMessage());
                }
            }
        });

    }
}

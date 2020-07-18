package com.example.sendo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    ListView listView;
    static List<String> usersList;
    UserListAdapter arrayAdapter;
    static List<String> following;
    Intent feedIntent;

    public void follow(View view)
    {
        String user = usersList.get((int)((Button)view).getTag());
        Log.i("Followed" , user );
        if( !following.contains(user)) {
            following.add(user);
            ((Button)view).setText("Unfollow");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.feed:
                startActivity(feedIntent);
                break;
            case R.id.logout:
                ParseUser.logOut();
                onBackPressed();
                break;
            case R.id.tweet:
                askForTweet();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void askForTweet()
    {
        final Dialog dialog = new Dialog(this );
        dialog.setContentView(R.layout.alertdialog);
        Button send = (Button) dialog.findViewById(R.id.sendTweet);
        final EditText textForTweet = (EditText) dialog.findViewById(R.id.tweetEditText);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject parseObject = new ParseObject("Tweet");
                parseObject.put("username" , ParseUser.getCurrentUser().getUsername());
                parseObject.put("tweet" , textForTweet.getText().toString());
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null)
                        {
                            Toast.makeText(UserListActivity.this, "Tweet sent", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(UserListActivity.this, "Tweet not sent", Toast.LENGTH_SHORT).show();
                            Log.i("Tweet not sent" , e.getMessage());
                        }
                    }
                });
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Intent intent = getIntent();
        feedIntent = new Intent(UserListActivity.this , FeedActivity.class );
        usersList = new ArrayList();
        following = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new UserListAdapter(this ,R.layout.userlist, usersList);
        listView.setAdapter(arrayAdapter);

        ParseQuery<ParseUser> userParseQuery = ParseUser.getQuery();
        System.out.println(intent.getStringExtra("username"));
        userParseQuery.whereNotEqualTo("username" , (Object)intent.getStringExtra("username"));
        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null)
                {
                    Log.i("Getting Users List" , "Success");
                    for(ParseUser object : objects)
                    {
                        usersList.add(object.getUsername());
                        arrayAdapter.notifyDataSetChanged();
                    }
                    System.out.println(usersList.toString());
                }
                else {
                    Log.i("Getting Users List" , "Failed");
                }
            }
        });
    }
}

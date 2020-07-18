package com.example.sendo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<String> {


    private int resourceLayout;
    private Context mContext;

    public UserListAdapter(Context context, int resource, List<String> items) {
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
        Button button = (Button) v.findViewById(R.id.followbtn);
        button.setTag(position);
        TextView userName = (TextView) v.findViewById(R.id.usernameTextView);
        userName.setText(UserListActivity.usersList.get(position));
        return v;
    }

}
package com.example.sendo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText usernameText , passwordText;
    Button button;
    TextView textView;
    Intent intent;

    public void enter(View view)
    {
        final String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        ParseUser parseUser = new ParseUser();
        if(button.getText().toString().equalsIgnoreCase("Sign Up")) {
            parseUser.setUsername(username);
            parseUser.setPassword(password);
            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Sign up Successfull", Toast.LENGTH_SHORT).show();
                        intent.putExtra("username" , username);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("Signup Failed", e.toString());
                    }
                }
            });
        }
        else
        {
            parseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null && e == null)
                    {
                        Toast.makeText(MainActivity.this, "Login sucess", Toast.LENGTH_SHORT).show();
                        intent.putExtra("username" , username);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void changeButtonText(View view)
    {
        if(button.getText().toString().equalsIgnoreCase("Sign Up")) {
            button.setText("Log In");
            textView.setText("New User?");
        }
        else {
            button.setText("Sign Up");
            textView.setText("Already a User?");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this , UserListActivity.class);
        if(ParseUser.getCurrentUser() != null)
        {
            intent.putExtra("username" , ParseUser.getCurrentUser().getUsername());
            startActivity(intent);
            finish();
        }
        usernameText =(EditText) findViewById(R.id.usernameEditText);
        passwordText =(EditText) findViewById(R.id.passwordEditText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);


    }
}

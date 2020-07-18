package com.example.sendo;
import com.parse.Parse;
import android.app.Application;
public class Server extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("yourAppId")
                .clientKey("YourClientKey")
                .server("YourServer")
                .build()
        );
    }
}
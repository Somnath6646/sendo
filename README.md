
# Sendo

A social media app to send public text announcements similar to twitter.




## Run Locally

Clone the project

```bash
  git clone https://github.com/Somnath6646/sendo
```

Open the cloned folder in android studio and then change the following in Server.java



Enter the app Id and client key of your parse server

```bash
    
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


  
  

  

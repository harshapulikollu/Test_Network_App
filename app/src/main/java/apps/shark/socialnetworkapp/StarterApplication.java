package apps.shark.socialnetworkapp;

/**
 * Created by Harsha on 7/26/2017.
 */
import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("9732e47b2539a77ffdfecac243843e15b552e470")
                .clientKey("2186c420387c1d351ed103ef96509522851cbfc4")
                .server("http://ec2-52-15-140-201.us-east-2.compute.amazonaws.com:80/parse/")
                .build()
        );



      //  ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}

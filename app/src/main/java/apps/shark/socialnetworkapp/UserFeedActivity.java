package apps.shark.socialnetworkapp;

/**
 * Created by Harsha on 7/26/2017.
 */


import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    String activeUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        TextView profilename = (TextView) findViewById(R.id.profileName);
        final ImageView profilepic = (ImageView) findViewById(R.id.profilePic);
        final TextView postsNumber =  (TextView) findViewById(R.id.posts);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        Intent intent = getIntent();

         activeUsername = intent.getStringExtra("username");

        profilename.setText(activeUsername);
        setTitle(activeUsername + "'s Feed");
//for profile pic
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("profilePicture");
        query.whereEqualTo("username", activeUsername);
        query.orderByDescending("createdAt");
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size()>0){
                        Log.i("objectsize ",Integer.toString(objects.size()));
                        for (ParseObject object : objects){
                            ParseFile file = (ParseFile) object.get("profilepic");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if (e == null && data != null) {


                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        profilepic.setImageBitmap(bitmap);

                                    }


                                }
                            });
                        }
                    }
                }
            }
        });


        ParseQuery<ParseObject> Imagequery = new ParseQuery<ParseObject>("Image");

        Imagequery.whereEqualTo("username", activeUsername);
        Imagequery.orderByDescending("createdAt");

        Imagequery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                int numofposts = objects.size();
                postsNumber.setText(Integer.toString(numofposts));
                if (e == null) {

                    if (objects.size() > 0) {


                        for (ParseObject object : objects) {

                            ParseFile file = (ParseFile) object.get("image");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if (e == null && data != null) {


                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        ImageView imageView = new ImageView(getApplicationContext());

                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));

                                        imageView.setImageBitmap(bitmap);

                                        linearLayout.addView(imageView);

                                    }


                                }
                            });

                        }

                    }

                }

            }
        });




    }



}

package apps.shark.socialnetworkapp;

/**
 * Created by Harsha on 7/26/2017.
 */


import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFeedActivity extends AppCompatActivity {

    String activeUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        TextView profilename = (TextView) findViewById(R.id.profileName);
        final CircleImageView profilepic = (CircleImageView) findViewById(R.id.profilePic);
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

                                       // CardView cardView = new CardView(getApplicationContext());
                                     /*   cardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT)); */
                                        CardView.LayoutParams layoutParams = new CardView.LayoutParams(
                                                CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
                                        layoutParams.setMargins(75,75,75,0);
                                        CardView cv = new CardView(getBaseContext());
                                        cv.setLayoutParams(layoutParams);
                                       // cardView.setRadius(15);



                                       // cardView.setPadding(25, 25, 25, 25);

                                       // cv.setCardBackgroundColor(Color.MAGENTA);

                                        cv.setMaxCardElevation(30);

                                       // cardView.setMaxCardElevation(6);
                                        ImageView imageView = new ImageView(getApplicationContext());

                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));

                                        imageView.setImageBitmap(bitmap);
                                       cv.addView(imageView);
                                        // cardView.addView(imageView);
                                       // linearLayout.addView(imageView);
                                      //  linearLayout.addView(cardView);
                                            linearLayout.addView(cv);
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

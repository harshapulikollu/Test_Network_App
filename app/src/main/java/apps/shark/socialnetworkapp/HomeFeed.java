package apps.shark.socialnetworkapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsha on 8/4/2017.
 */

public class HomeFeed extends AppCompatActivity {
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;

    String appUsername;//commented on 6th aug
    String user;
    ArrayList<String> Following;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_home_feed);

        listView = (ListView) findViewById(R.id.card_listView);

        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);








        //final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        appUsername= ParseUser.getCurrentUser().getUsername().toString();

        ParseQuery<ParseObject> Imagequery = new ParseQuery<ParseObject>("Image");

       // Imagequery.whereEqualTo("username", activeUsername);
        Imagequery.orderByDescending("createdAt");

        Imagequery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                int numofposts = objects.size();
                //  postsNumber.setText(Integer.toString(numofposts));
                if (e == null) {

                    if (objects.size() > 0) {


                        for (ParseObject object : objects) {

                            ParseFile file = (ParseFile) object.get("image");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if (e == null && data != null) {


                                       Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        Card card = new Card(bitmap);
                                        cardArrayAdapter.add(card);

                                       /* CardView.LayoutParams layoutParams = new CardView.LayoutParams(
                                              CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
                                        layoutParams.setMargins(25,25,25,0);

                                        CardView cv = new CardView(getBaseContext());

                                        cv.setLayoutParams(layoutParams);
                                        // cardView.setRadius(15);
                                        cv.setMaxCardElevation(30);

                                        ImageView imageView =new ImageView(getApplicationContext());

                                       imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));

                                        imageView.setImageBitmap(bitmap);
                                        cv.addView(imageView);

                                        linearLayout.addView(cv);*/
                                    }



                                }
                            });

                        }
                        listView.setAdapter(cardArrayAdapter);
                    }

                }

            }
        });





    }
}

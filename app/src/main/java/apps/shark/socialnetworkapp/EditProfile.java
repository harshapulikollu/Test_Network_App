package apps.shark.socialnetworkapp;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Harsha on 7/27/2017.
 */

public class EditProfile extends AppCompatActivity {

     CircleImageView ProfilePic;
    ProgressBar progressBar;

    public void getPhoto() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getPhoto();

            }


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {

                final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                Log.i("newProfilePhoto", "Received");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] byteArray = stream.toByteArray();

                ParseFile file = new ParseFile("dpimage.png", byteArray);

                ParseObject object = new ParseObject("profilePicture");

                object.put("profilepic", file);

                object.put("username", ParseUser.getCurrentUser().getUsername());

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        progressBar.setVisibility(View.INVISIBLE);
                        if (e == null) {

                            Toast.makeText(EditProfile.this, "New profile pic added!", Toast.LENGTH_SHORT).show();
                            ProfilePic.setImageBitmap(bitmap);

                        } else {

                            Toast.makeText(EditProfile.this, "Image could not be added - please try again later.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
         ProfilePic = (CircleImageView) findViewById(R.id.profile_pic);
        ImageButton addNewDp = (ImageButton)findViewById(R.id.Add_new_dp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        addNewDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                        getPhoto();

                    }

                } else {

                    getPhoto();

                }
            }
        });

        Intent intent = getIntent();

        String activeUsername = intent.getStringExtra("username");
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

                                        ProfilePic.setImageBitmap(bitmap);

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

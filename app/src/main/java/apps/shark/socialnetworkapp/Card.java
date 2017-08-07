package apps.shark.socialnetworkapp;

import android.graphics.Bitmap;

/**
 * Created by Harsha on 8/7/2017.
 */

public class Card {
    private Bitmap imageview;
    //private String line2;

    public Card(Bitmap imageview) {
        this.imageview = imageview;
        ///this.line2 = line2;
    }

    public Bitmap getImageView() {
        return imageview;
    }

  /*  public String getLine2() {
        return line2;
    } */

}
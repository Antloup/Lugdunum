package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.lugdunum.heptartuflette.lugdunum.Provider.RecentPhotoProvider;
import com.lugdunum.heptartuflette.lugdunum.R;
import com.lugdunum.heptartuflette.lugdunum.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class CompareOldPhoto extends AppCompatActivity {

    private Bitmap oldPhotoBitmap;
    private Bitmap recentPhotoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_old_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button buttonVote = (Button) findViewById(R.id.buttonVoter);
        buttonVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingBar voteBar = (RatingBar) findViewById(R.id.ratingPhoto);
                float rating = voteBar.getRating();
                RecentPhotoProvider photoProvider = new RecentPhotoProvider();
                photoProvider.postVote(getIntent().getIntExtra("id",0),rating);
                String vote = "Votre vote : " + rating;
                Snackbar mySnackbar = Snackbar.make(view,
                        vote, Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }
        });


        //Fill up view
//        oldPhotoBitmap = (Bitmap) getIntent().getParcelableExtra("oldPhotoBitmap");
        String filename = getIntent().getStringExtra("oldPhotoName");
        try {
            FileInputStream is = this.openFileInput(filename);
            oldPhotoBitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView oldImageView = (ImageView) findViewById(R.id.imageViewOld);
        oldImageView.setImageBitmap(oldPhotoBitmap);

        filename = getIntent().getStringExtra("recentPhotoName");
        try {
            FileInputStream is = this.openFileInput(filename);
            recentPhotoBitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        recentPhotoBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("recentPhotoName"));
//        recentPhotoBitmap = (Bitmap) getIntent().getParcelableExtra("recentPhotoBitmap");
        ImageView recentImageView = (ImageView) findViewById(R.id.imageViewRecent);
        recentImageView.setImageBitmap(recentPhotoBitmap);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}

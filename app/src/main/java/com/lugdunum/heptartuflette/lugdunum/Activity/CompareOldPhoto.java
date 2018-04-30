package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.lugdunum.heptartuflette.lugdunum.R;
import com.lugdunum.heptartuflette.lugdunum.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class CompareOldPhoto extends AppCompatActivity {

    private Bitmap oldPhotoBitmap;

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
                String string1 = "Rating = " + rating;
                Snackbar mySnackbar = Snackbar.make(view,
                        string1, Snackbar.LENGTH_SHORT);
                mySnackbar.show();

            //TODO : Envoyer la note sur le serveur
            }
        });


        //Fill up view
        oldPhotoBitmap = (Bitmap) getIntent().getParcelableExtra("oldPhotoBitmap");
        ImageView oldImageView = (ImageView) findViewById(R.id.imageViewOld);
        oldImageView.setImageBitmap(oldPhotoBitmap);
    }
}

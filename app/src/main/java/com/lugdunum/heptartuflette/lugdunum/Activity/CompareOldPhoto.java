package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lugdunum.heptartuflette.lugdunum.R;

public class CompareOldPhoto extends AppCompatActivity {

    private Bitmap oldPhotoBitmap;
    private Bitmap recentPhotoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_old_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Fill up view
        oldPhotoBitmap = (Bitmap) getIntent().getParcelableExtra("oldPhotoBitmap");
        ImageView oldImageView = (ImageView) findViewById(R.id.imageViewOld);
        oldImageView.setImageBitmap(oldPhotoBitmap);
        recentPhotoBitmap = (Bitmap) getIntent().getParcelableExtra("recentPhotoBitmap");
        ImageView recentImageView = (ImageView) findViewById(R.id.imageViewRecent);
        recentImageView.setImageBitmap(recentPhotoBitmap);
    }
}

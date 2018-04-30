package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.lugdunum.heptartuflette.lugdunum.R;

public class TakePhoto extends AppCompatActivity {

    private TextView mTextMessage;
    private Bitmap oldPhotoBitmap;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ImageView iv = (ImageView)findViewById(R.id.imageViewCamera);

        /* Fill up view */

        // Put camera picture
        if(getIntent().hasExtra("imageByteArray")) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("imageByteArray"),0,
                    getIntent().getByteArrayExtra("imageByteArray").length
            );
            iv.setImageBitmap(bitmap);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Put OldPhoto picture
        oldPhotoBitmap = (Bitmap) getIntent().getParcelableExtra("oldPhotoBitmap");
        ImageView oldImageView = (ImageView) findViewById(R.id.imageViewOld);
        oldImageView.setImageBitmap(oldPhotoBitmap);

    }

}

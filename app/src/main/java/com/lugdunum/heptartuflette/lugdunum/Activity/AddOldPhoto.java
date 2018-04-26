package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.lugdunum.heptartuflette.lugdunum.R;

public class AddOldPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_old_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView img = (ImageView) findViewById(R.id.oldImg);
        img.setImageBitmap(BitmapFactory.decodeFile(getIntent().getStringExtra("picturePath")));
        // TODO: pop the current activity and don't reload main activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

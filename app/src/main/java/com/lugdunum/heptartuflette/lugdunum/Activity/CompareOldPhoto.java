package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lugdunum.heptartuflette.lugdunum.R;

public class CompareOldPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_old_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

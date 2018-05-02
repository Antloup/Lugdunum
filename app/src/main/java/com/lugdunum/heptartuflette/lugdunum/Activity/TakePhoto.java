package com.lugdunum.heptartuflette.lugdunum.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lugdunum.heptartuflette.lugdunum.Model.RecentPhoto;
import com.lugdunum.heptartuflette.lugdunum.Provider.RecentPhotoProvider;
import com.lugdunum.heptartuflette.lugdunum.R;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Date;

public class TakePhoto extends AppCompatActivity {

    private TextView mTextMessage;
    private Bitmap oldPhotoBitmap;
    private Bitmap recentPhotoBitmap;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ImageView iv = (ImageView)findViewById(R.id.imageViewCamera);

        /* Fill up view */

        // Put camera picture
//        if(getIntent().hasExtra("imageByteArray")) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(
//                    getIntent().getByteArrayExtra("imageByteArray"),0,
//                    getIntent().getByteArrayExtra("imageByteArray").length
//            );
//            iv.setImageBitmap(bitmap);
//
//        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String filename = getIntent().getStringExtra("imageName");
        try {
            FileInputStream is = this.openFileInput(filename);
            recentPhotoBitmap = BitmapFactory.decodeStream(is);
            RequestOptions opt = new RequestOptions();
            opt.fitCenter();
            Glide.with(this).load(recentPhotoBitmap).apply(opt).into(iv);

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        filename = getIntent().getStringExtra("oldPhotoName");
        try {
            FileInputStream is = this.openFileInput(filename);
            oldPhotoBitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Put OldPhoto picture
//        oldPhotoBitmap = (Bitmap) getIntent().getParcelableExtra("oldPhotoBitmap");
        ImageView oldImageView = (ImageView) findViewById(R.id.imageViewOld);
        oldImageView.setImageBitmap(oldPhotoBitmap);

        Button saveButton= (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(v, "Photo sauvegardée !", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        Button uploadButton= (Button) findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentPhotoProvider recentPhotoProvider = new RecentPhotoProvider();
                //TODO: Fill Photo
                int id = getIntent().getIntExtra("idPlace",0);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                recentPhotoBitmap.compress(Bitmap.CompressFormat.JPEG,10,baos);
                recentPhotoBitmap = BitmapFactory.decodeByteArray(baos.toByteArray(),0, baos.toByteArray().length);
                recentPhotoProvider.postPhoto(new RecentPhoto("NAME","FORMAT",recentPhotoBitmap,new Date()),id);
            }
        });

    }

}
